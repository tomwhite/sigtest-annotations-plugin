package name.tomwhite.sigtest;

import com.sun.tdk.signaturetest.plugin.Plugin;
import com.sun.tdk.signaturetest.plugin.PluginAPI;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExcludeAnnotationsPlugin implements Plugin {
  
  private final ApiDefinition apiDefinition =
    new ApiDefinition(getExcludedAnnotations());
  
  private static Set<String> getExcludedAnnotations() {
    String excludedAnnotations =
      System.getProperty("name.tomwhite.sigtest.excludedAnnotations");
    if (excludedAnnotations == null) {
      return new HashSet<String>();
    }
    List<String> names = Arrays.asList(excludedAnnotations.split(","));
    return new HashSet<String>(names);
  }
  
  @Override
  public void init(PluginAPI api) {
    api.setFilter(PluginAPI.IS_CLASS_ACCESSIBLE, new ApiClassFilter(apiDefinition));
    api.setTransformer(PluginAPI.AFTER_CLASS_CORRECTOR, new ApiClassCorrector(apiDefinition));
  }

  @Override
  public void release() {
    // do nothing
  }

}
