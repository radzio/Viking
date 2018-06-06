package agency.tango.viking.processor;

import com.google.common.collect.ImmutableSet;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Ignore;
import org.junit.Test;
import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

public class VikingCodeProcessorTest {
  @Test
  public void generatesActivityFragmentsModule() {
    JavaFileObject testActivity = JavaFileObjects.forSourceString("test.TestActivity",
        "package test;\n"
            + "import agency.tango.viking.annotations.AutoModule;\n"
            + "\n"
            + "@AutoModule\n"
            + "public class TestActivity {\n"
            + "    \n"
            + "}");

    JavaFileObject testFragment = JavaFileObjects.forSourceString("test.TestFragment",
        "package test;\n"
            + "import agency.tango.viking.annotations.AutoModule;\n"
            + "\n"
            + "@AutoModule(scopes = {TestActivity.class})\n"
            + "public class TestFragment {\n"
            + "    \n"
            + "}");

    JavaFileObject expectedActivityFragmentsModule = JavaFileObjects.forSourceString("test.ActivityFragments_Module",
        "package test;\n"
            + "import dagger.Module;\n"
            + "import dagger.android.ContributesAndroidInjector;\n"
            + "\n"
            + "@Module\n"
            + "public abstract class TestActivityFragments_Module {\n"
            + "  @ContributesAndroidInjector\n"
            + "  public abstract TestFragment providesTestFragment();\n"
            + "}");

    assertAbout(javaSources())
        .that(ImmutableSet.of(testActivity, testFragment))
        .processedWith(new VikingCodeProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedActivityFragmentsModule);
  }

  @Ignore
  @Test
  public void generatesTypeAdapterFactory() {

    JavaFileObject checkingFragment = JavaFileObjects.forSourceString("test.CheckinsFragment",
        "package test;\n"
            + "import agency.tango.viking.annotations.AutoModule;\n"
            + "\n"
            + "@AutoModule\n"
            + "public class CheckinsFragment {\n"
            + "    \n"
            + "}");

    JavaFileObject expectedCheckinsFragmentModule = JavaFileObjects.forSourceString(
        "test.CheckinsFragment_Module",
        "package test;\n"
            + "\n"
            + "import agency.tango.viking.di.ScreenModule;\n"
            + "import android.content.Context;\n"
            + "import dagger.Module;\n"
            + "import net.droidlabs.dagger.annotations.ActivityScope;\n"
            + "\n"
            + "@ActivityScope\n"
            + "@Module\n"
            + "public class CheckinsFragment_Module extends ScreenModule<CheckinsFragment> {\n"
            + "  CheckinsFragment_Module(Context context, CheckinsFragment screen) {\n"
            + "    super(context, screen);\n"
            + "  }\n"
            + "}");

    assertAbout(javaSources())
        .that(ImmutableSet.of(checkingFragment))
        .processedWith(new VikingCodeProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedCheckinsFragmentModule);
  }

  @Ignore
  @Test
  public void generatesTypeAdapterFactory2() {

    JavaFileObject baseModule = JavaFileObjects.forSourceString("test.BaseModule",
        "package test;\n"
            + "\n"
            + "public class BaseModule {\n"
            + "  public BaseModule(String a, String b) {\n"
            + "\n"
            + "  }\n"
            + "}"
    );

    JavaFileObject checkingFragment = JavaFileObjects.forSourceString("test.CheckinsFragment",
        "package test;\n"
            + "import agency.tango.viking.annotations.AutoModule;\n"
            + "import test.BaseModule;\n"
            + "\n"
            + "@AutoModule(superClass = BaseModule.class)\n"
            + "public class CheckinsFragment {\n"
            + "    \n"
            + "}");

    JavaFileObject expectedCheckinsFragmentModule = JavaFileObjects.forSourceString(
        "test.CheckinsFragment_Module",
        "package test;\n"
            + "\n"
            + "import dagger.Module;\n"
            + "import java.lang.String;\n"
            + "import net.droidlabs.dagger.annotations.ActivityScope;\n"
            + "\n"
            + "@ActivityScope\n"
            + "@Module\n"
            + "public class CheckinsFragment_Module extends BaseModule {\n"
            + "  public CheckinsFragment_Module(String a, String b) {\n"
            + "    super(a, b);\n"
            + "  }\n"
            + "}");

    assertAbout(javaSources())
        .that(ImmutableSet.of(baseModule, checkingFragment))
        .processedWith(new VikingCodeProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedCheckinsFragmentModule);
  }
}