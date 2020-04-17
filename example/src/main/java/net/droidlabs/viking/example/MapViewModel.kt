package net.droidlabs.viking.example

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.StaticCluster
import com.google.maps.android.heatmaps.HeatmapTileProvider
import net.droidlabs.viking.bindings.map.InfoWindowAdapterFactory
import net.droidlabs.viking.bindings.map.RendererFactory
import net.droidlabs.viking.bindings.map.adapters.CustomInfoWindowAdapter
import net.droidlabs.viking.bindings.map.listeners.ItemClickListener
import net.droidlabs.viking.bindings.map.listeners.OnMarkerClickListener
import net.droidlabs.viking.bindings.map.models.BindableCircle
import net.droidlabs.viking.bindings.map.models.BindableMarker
import net.droidlabs.viking.bindings.map.models.BindableOverlay
import net.droidlabs.viking.bindings.map.models.BindablePolygon
import net.droidlabs.viking.bindings.map.models.BindablePolyline
import net.droidlabs.viking.mvvm.StartupAction
import net.droidlabs.viking.mvvm.ViewModel
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class MapViewModel @Inject
internal constructor(context: Context,
                     @Named("test_string") test: String,
                     @Named("second_test_string") secondTest: String
) : ViewModel() {

    @get:Bindable
    val models = ObservableArrayList<BindableMarker<ExampleModel>>()

    @get:Bindable
    val polylines = ObservableArrayList<BindablePolyline>()

    @get:Bindable
    val overlays = ObservableArrayList<BindableOverlay>()

    @get:Bindable
    val circles = ObservableArrayList<BindableCircle>()

    @get:Bindable
    val polygons = ObservableArrayList<BindablePolygon>()

    @get:Bindable
    val clusterItems = ObservableArrayList<ClusterModel>()

    @get:Bindable
    var heatMap: HeatmapTileProvider? = null
        private set

    // we need <LatLng?> because of https://youtrack.jetbrains.com/issue/KT-10930
    @get:Bindable
    var latLng: LatLng by observable<LatLng>(LatLng(45.0,45.0), BR.latLng) {
        Log.d("Changed", it.toString())
        notifyPropertyChanged(BR.location)
    }

    @get:Bindable
    var bounds: LatLngBounds? by observable(null, BR.bounds)

    @get:Bindable
    var padding: Float by observable(0F, BR.padding)

    @get:Bindable
    var zoom: Float by observable(0F, BR.zoom) {
        Log.d("Changed zoom", it.toString())
    }

    @get:Bindable("latLng")
    val location: String
        get () {
            return latLng.toString()
        }


    var testSingleLiveEvent: MutableLiveData<NavigatorOperation> = SingleLiveEvent()


    val markerClickListener: OnMarkerClickListener<BindableMarker<ExampleModel>>
        @Bindable
        get() = OnMarkerClickListener { item ->
            item.marker!!.showInfoWindow()

            testSingleLiveEvent.postValue(NavigatorOperation { navigator -> navigator.openMainActivity() })

            true
        }

    val infoWindowClickListener: ItemClickListener<BindableMarker<ExampleModel>>
        @Bindable
        get() = ItemClickListener { item -> item.marker!!.hideInfoWindow() }

    val polylineClickListener: ItemClickListener<BindablePolyline>
        @Bindable
        get() = ItemClickListener { item -> item.polyline!!.width = 50f }

    val overlayClickListener: ItemClickListener<BindableOverlay>
        @Bindable
        get() = ItemClickListener { item -> item.groundOverlay!!.position = LatLng(0.0, 0.0) }

    val polygonClickListener: ItemClickListener<BindablePolygon>
        @Bindable
        get() = ItemClickListener { item -> item.polygon!!.fillColor = R.color.colorAccent }

    val circleClickListener: ItemClickListener<BindableCircle>
        @Bindable
        get() = ItemClickListener { item -> item.circle!!.fillColor = R.color.colorAccent }

    val infoWindowAdapter: InfoWindowAdapterFactory<BindableMarker<ExampleModel>>
        @Bindable
        get() = InfoWindowAdapterFactory { context ->
            object : CustomInfoWindowAdapter<BindableMarker<ExampleModel>> {
                override fun getInfoWindow(bindableMarker: BindableMarker<ExampleModel>): View? {
                    return null
                }

                override fun getInfoContents(bindableMarker: BindableMarker<ExampleModel>): View {
                    val view = LayoutInflater.from(context).inflate(R.layout.info_window, null)

                    val title = view.findViewById<View>(R.id.tv_title) as TextView
                    val description = view.findViewById<View>(R.id.tv_description) as TextView

                    title.text = bindableMarker.getObject().title
                    description.text = bindableMarker.getObject().description
                    return view
                }
            }
        }

    val clusterItemInfoWindowAdapter: InfoWindowAdapterFactory<ClusterItem>
        @Bindable
        get() = InfoWindowAdapterFactory { context ->
            object : CustomInfoWindowAdapter<ClusterItem> {
                override fun getInfoWindow(clusterItem: ClusterItem): View? {
                    return null
                }

                override fun getInfoContents(clusterItem: ClusterItem): View {
                    val view = LayoutInflater.from(context).inflate(R.layout.info_window, null)

                    val title = view.findViewById<View>(R.id.tv_title) as TextView

                    title.text = String.format("LatLng: %s", clusterItem.position.toString())
                    return view
                }
            }
        }

    val clusterInfoWindowAdapter: InfoWindowAdapterFactory<StaticCluster<*>>
        @Bindable
        get() = InfoWindowAdapterFactory { context ->
            object : CustomInfoWindowAdapter<StaticCluster<*>> {
                override fun getInfoWindow(cluster: StaticCluster<*>): View? {
                    return null
                }

                override fun getInfoContents(cluster: StaticCluster<*>): View {
                    val view = LayoutInflater.from(context).inflate(R.layout.info_window, null)

                    val title = view.findViewById<View>(R.id.tv_title) as TextView
                    val description = view.findViewById<View>(R.id.tv_description) as TextView

                    title.text = String.format("SIZE: %d", cluster.items.size)
                    description.text = String.format("LatLng: %f %f", cluster.position.latitude,
                            cluster.position.longitude)
                    return view
                }
            }
        }

    val clusterClickListener: ClusterManager.OnClusterClickListener<ClusterModel>
        @Bindable
        get() = object : ClusterManager.OnClusterClickListener<ClusterModel> {
            override fun onClusterClick(cluster: Cluster<ClusterModel>): Boolean {
                latLng = cluster.position
                return false
            }
        }

    val rendererFactory: RendererFactory<ClusterModel>
        @Bindable
        get() = RendererFactory { context, googleMap, clusterManager -> CustomClusterRenderer(context, googleMap, clusterManager) }

    init {
        Log.d("MapViewModel", "$this $context")
        Log.d("MapViewModel", test)
        runOnStartup(object : StartupAction {
            override fun execute() {
                clusterItems.add(ClusterModel(LatLng(0.0, 11.101110)))
                clusterItems.add(ClusterModel(LatLng(0.0, 12.202222)))
                clusterItems.add(ClusterModel(LatLng(0.0, 13.303334)))
                clusterItems.add(ClusterModel(LatLng(3.0, 11.101110)))
                clusterItems.add(ClusterModel(LatLng(3.0, 12.202222)))
                clusterItems.add(ClusterModel(LatLng(3.0, 13.303334)))

                circles.add(BindableCircle(CircleOptions()
                        .radius(100000.0)
                        .clickable(true)
                        .center(LatLng(0.0, 5.0))))

                val exampleModel = ExampleModel("Hello", "World")
                models.add(BindableMarker(
                        exampleModel,
                        MarkerOptions()
                                .title("marker")
                                .position(LatLng(0.0, -10.0))))

                polylines.add(BindablePolyline(
                        PolylineOptions()
                                .clickable(true)
                                .add(LatLng(0.0, 25.0))
                                .add(LatLng(0.0, 30.0))))

                polygons.add(BindablePolygon(PolygonOptions()
                        .clickable(true)
                        .strokeColor(Color.rgb(255, 0, 0))
                        .add(LatLng(0.0, 0.0))
                        .add(LatLng(0.0, 1.0))
                        .add(LatLng(1.0, 1.0))
                        .add(LatLng(1.0, 0.0))))

                overlays.add(BindableOverlay(
                        GroundOverlayOptions()
                                .image(BitmapDescriptorFactory.fromResource(R.drawable.heart))
                                .positionFromBounds(LatLngBounds(LatLng(0.0, -4.0), LatLng(1.0, -3.0)))))

                heatMap = HeatmapTileProvider.Builder()
                        .data(Arrays.asList(LatLng(0.0, 20.0),
                                LatLng(0.0, 21.0),
                                LatLng(0.0, 22.0)))
                        .radius(50)
                        .build()

                padding = 25f
                bounds = LatLngBounds(LatLng(51.2825839249167, 20.468222118914127), LatLng(51.661517199029426, 21.288533613085747))
                latLng = LatLng(45.0, 45.0)
            }
        })
    }

    companion object {
        private val DEFAULT_ZOOM = 2f
    }
}
