# Add any ProGuard configurations specific to this
# extension here.

-keep public class com.yash.mixpanel.mixpanelanalytics.MixPanelAnalytics {
    public *;
 }
-keeppackagenames gnu.kawa**, gnu.expr**

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses 'com/yash/mixpanel/mixpanelanalytics/repack'
-flattenpackagehierarchy
-dontpreverify
