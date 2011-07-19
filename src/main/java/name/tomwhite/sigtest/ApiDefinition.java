package name.tomwhite.sigtest;

import com.sun.tdk.signaturetest.model.AnnotationItem;
import com.sun.tdk.signaturetest.model.ClassDescription;
import com.sun.tdk.signaturetest.model.MemberDescription;

import java.util.HashSet;
import java.util.Set;

/**
 * This class encapsulates the rules about what constitutes the public API.
 *
 */
public class ApiDefinition {
  
  private final Set<String> excludedAnnotations;
  
  private Set<String> excludedPackagesCache = new HashSet<String>();

  public ApiDefinition(Set<String> excludedAnnotations) {
    this.excludedAnnotations = excludedAnnotations;
  }
  
  public boolean includes(MemberDescription member) {
    return includes(member.getAnnoList());
  }

  public boolean includes(ClassDescription cls) {
    if (cls.isPackageInfo()) {
      if (!includes(cls.getAnnoList())) {
        // remember excluded package, note this assumes package info is seen
        // before any classes in the package
        excludedPackagesCache.add(cls.getPackageName());
        return false;
      }
      return true;
    }
    return !excludedPackagesCache.contains(cls.getPackageName()) &&
      includes(cls.getAnnoList());
  }

  private boolean includes(AnnotationItem[] annoList) {
    for (AnnotationItem a : annoList) {
      if (excludedAnnotations.contains(a.getName())) {
        return false;
      }
    }
    return true;
  }

}
