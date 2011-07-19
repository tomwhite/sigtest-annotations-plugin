package name.tomwhite.sigtest;

import com.sun.tdk.signaturetest.model.ClassDescription;
import com.sun.tdk.signaturetest.model.MemberDescription;
import com.sun.tdk.signaturetest.plugin.Transformer;

import java.util.Iterator;

public class ApiClassCorrector implements Transformer {

  private ApiDefinition apiDefinition;
  
  public ApiClassCorrector(ApiDefinition apiDefinition) {
    this.apiDefinition = apiDefinition;
  }

  @Override
  public ClassDescription transform(ClassDescription cls)
      throws ClassNotFoundException {
    for (@SuppressWarnings("unchecked")
    Iterator<MemberDescription> it = cls.getMembersIterator(); it.hasNext();) {
      MemberDescription member = it.next();
      if (!apiDefinition.includes(member)) {
        it.remove();
      }
    }
    return cls;
  }

}
