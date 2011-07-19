package name.tomwhite.sigtest;

import com.sun.tdk.signaturetest.model.ClassDescription;
import com.sun.tdk.signaturetest.plugin.Filter;

public class ApiClassFilter implements Filter {

  private ApiDefinition apiDefinition;
  
  public ApiClassFilter(ApiDefinition apiDefinition) {
    this.apiDefinition = apiDefinition;
  }

  @Override
  public boolean accept(ClassDescription cls) {
    return apiDefinition.includes(cls);
  }

}
