package android.taobao.atlas.runtime;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.taobao.atlas.hack.AtlasHacks;
import android.taobao.atlas.log.Logger;
import android.taobao.atlas.log.LoggerFactory;
import android.taobao.atlas.util.StringUtils;
import android.util.AttributeSet;

//This Class can ref PackageParser
public class PackageLite {
    private static final String XMLDISABLECOMPONENT_SSO_ALIPAY_AUTHENTICATION_SERVICE = "com.taobao.android.sso.internal.AlipayAuthenticationService";
    private static final String XMLDISABLECOMPONENT_SSO_AUTHENTICATION_SERVICE = "com.taobao.android.sso.internal.AuthenticationService";
    static final Logger log;
    public String applicationClassName;
    public int applicationDescription;
    public int applicationIcon;
    public int applicationLabel;
    public final Set<String> components;
    public final Set<String> disableComponents;
    public Bundle metaData;
    public String packageName;
    public int versionCode;
    public String versionName;

    static {
        log = LoggerFactory.getInstance("PackageInfo");
    }

    PackageLite() {
        this.components = new HashSet();
        this.disableComponents = new HashSet();
    }

    public static PackageLite parse(File file) {
        Throwable e;
        XmlResourceParser xmlResourceParser = null;
        XmlResourceParser openXmlResourceParser;
        try {
            AssetManager assetManager = (AssetManager) AssetManager.class
                    .newInstance();
            int intValue = ((Integer) AtlasHacks.AssetManager_addAssetPath
                    .invoke(assetManager, file.getAbsolutePath())).intValue();
            if (intValue != 0) {
                openXmlResourceParser = assetManager.openXmlResourceParser(
                        intValue, "AndroidManifest.xml");
            } else {
                openXmlResourceParser = assetManager.openXmlResourceParser(
                        intValue, "AndroidManifest.xml");
            }
            if (openXmlResourceParser != null) {
                try {
                    PackageLite parse = parse(openXmlResourceParser);
                    if (parse == null) {
                        parse = new PackageLite();
                    }

                    openXmlResourceParser.close();
                    return parse;
                } catch (Exception e2) {
                    e = e2;
                    try {
                        log.error(
                                "Exception while parse AndroidManifest.xml >>>",
                                e);
                        if (openXmlResourceParser != null) {
                            openXmlResourceParser.close();
                        }
                        return null;
                    } catch (Throwable th) {
                        e = th;
                        xmlResourceParser = openXmlResourceParser;
                        if (xmlResourceParser != null) {
                            xmlResourceParser.close();
                        }
                        throw e;
                    }
                }
            }
            return null;
        } catch (Exception e3) {
            e = e3;
            openXmlResourceParser = null;
            log.error("Exception while parse AndroidManifest.xml >>>", e);
            return null;
        } catch (Throwable th2) {
            e = th2;
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }

        }
        return null;
    }

    protected static android.taobao.atlas.runtime.PackageLite parse(
            android.content.res.XmlResourceParser arg10)
            throws java.lang.Exception {

        int v2;
        PackageLite v0_1;
        int v0;
        int v9 = 3;
        int v7 = 2;
        PackageLite v3 = null;
        PackageLite v4 = new PackageLite();
        do {
            v0 = arg10.next();
            if (v0 == v7) {
                break;
            }
        } while (v0 != 1);

        if (v0 != v7) {
            PackageLite.log.error("No start tag found");
            v0_1 = v3;
        } else if (!arg10.getName().equals("manifest")) {
            PackageLite.log.error("No <manifest> tag");
            v0_1 = v3;
        } else {
            v4.packageName = ((AttributeSet) arg10).getAttributeValue(null,
                    "package");
            if (v4.packageName != null && v4.packageName.length() != 0) {
                v0 = 0;
                v2 = 0;
            } else {
                PackageLite.log.error("<manifest> does not specify package");
                return v3;
            }

            while (v0 < ((AttributeSet) arg10).getAttributeCount()) {
                String v5 = ((AttributeSet) arg10).getAttributeName(v0);
                if (v5.equals("versionCode")) {
                    v4.versionCode = ((AttributeSet) arg10)
                            .getAttributeIntValue(v0, 0);
                    ++v2;
                } else if (v5.equals("versionName")) {
                    v4.versionName = ((AttributeSet) arg10)
                            .getAttributeValue(v0);
                    ++v2;
                }

                if (v2 >= v7) {
                    break;
                }

                ++v0;
            }

            v0 = arg10.getDepth() + 1;
            while (true) {
                int v1 = arg10.next();
                System.out.println(arg10.getName());
                if (v1 != XmlPullParser.END_DOCUMENT) {
                    if (arg10.getName().equals("application")) {
                        if (!PackageLite
                                .parseApplication(v4, ((XmlPullParser) arg10),
                                        ((AttributeSet) arg10))) {
                            return v3;
                        }

                        return v4;
                    }

                    if (v1 == v9 && arg10.getDepth() < v0) {
                        break;
                    }

                    if (v1 == v9) {
                        continue;
                    }

                    if (v1 == 4) {
                        continue;
                    }

                    PackageLite.skipCurrentTag(((XmlPullParser) arg10));
                    continue;
                }

                break;
            }

            v0_1 = v4;
        }

        return v0_1;

    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean parseApplication(PackageLite packageLite,
            XmlPullParser xmlPullParser, AttributeSet attributeSet)
            throws Exception {
        int i;
        String str = packageLite.packageName;
        for (i = 0; i < attributeSet.getAttributeCount(); i++) {
            String attributeName = attributeSet.getAttributeName(i);
            if (attributeName.equals("name")) {
                packageLite.applicationClassName = buildClassName(str,
                        attributeSet.getAttributeValue(i));
            } else if (attributeName.equals("icon")) {
                packageLite.applicationIcon = attributeSet
                        .getAttributeResourceValue(i, 0);
            } else if (attributeName.equals("label")) {
                packageLite.applicationLabel = attributeSet
                        .getAttributeResourceValue(i, 0);
            } else if (attributeName.equals("description")) {
                packageLite.applicationDescription = attributeSet
                        .getAttributeResourceValue(i, 0);
            }
        }
        i = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                if (next == 3 && xmlPullParser.getDepth() <= i) {
                    break;
                } else if (next != 3 && next != 4) {
                    str = xmlPullParser.getName();
                    if (str.equals("meta-data")) {
                        packageLite.metaData = parseMetaData(xmlPullParser,
                                attributeSet, packageLite.metaData);
                    } else if (str.equals("activity")) {
                        parseComponentData(packageLite, xmlPullParser,
                                attributeSet, false);
                    } else if (str.equals("receiver")) {
                        parseComponentData(packageLite, xmlPullParser,
                                attributeSet, true);
                    } else if (str.equals("service")) {
                        parseComponentData(packageLite, xmlPullParser,
                                attributeSet, true);
                    } else if (str.equals("provider")) {
                        parseComponentData(packageLite, xmlPullParser,
                                attributeSet, false);
                    } else {
                        skipCurrentTag(xmlPullParser);
                    }
                }
            } else {
                break;
            }
        }
        return true;
    }

    private static Bundle parseMetaData(XmlPullParser xmlPullParser,
            AttributeSet attributeSet, Bundle bundle)
            throws XmlPullParserException, IOException {
        int i = 0;
        if (bundle == null) {
            bundle = new Bundle();
        }
        String str = null;
        String str2 = null;
        int i2 = 0;
        while (i < attributeSet.getAttributeCount()) {
            String attributeName = attributeSet.getAttributeName(i);
            if (attributeName.equals("name")) {
                str2 = attributeSet.getAttributeValue(i);
                i2++;
            } else if (attributeName.equals("value")) {
                str = attributeSet.getAttributeValue(i);
                i2++;
            }
            if (i2 >= 2) {
                break;
            }
            i++;
        }
        if (!(str2 == null || str == null)) {
            bundle.putString(str2, str);
        }
        return bundle;
    }

    private static String buildClassName(String str, CharSequence charSequence) {
        if (charSequence == null || charSequence.length() <= 0) {
            log.error("Empty class name in package " + str);
            return null;
        }
        String obj = charSequence.toString();
        char charAt = obj.charAt(0);
        if (charAt == '.') {
            return (str + obj).intern();
        }
        if (obj.indexOf(46) < 0) {
            StringBuilder stringBuilder = new StringBuilder(str);
            stringBuilder.append('.');
            stringBuilder.append(obj);
            return stringBuilder.toString().intern();
        } else if (charAt >= 'a' && charAt <= 'z') {
            return obj.intern();
        } else {
            log.error("Bad class name " + obj + " in package " + str);
            return null;
        }
    }

    private static void skipCurrentTag(XmlPullParser xmlPullParser)
            throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
        }
    }

    private static void parseComponentData(PackageLite packageLite,
            XmlPullParser xmlPullParser, AttributeSet attributeSet, boolean z)
            throws XmlPullParserException {
        int i = 0;
        String str = packageLite.packageName;
        int i2 = 0;
        while (i < attributeSet.getAttributeCount()) {
            if (attributeSet.getAttributeName(i).equals("name")) {
                String attributeValue = attributeSet.getAttributeValue(i);
                if (attributeValue.startsWith(".")) {
                    attributeValue = str.concat(str);
                }
                packageLite.components.add(attributeValue);
                if (z
                        && !(StringUtils
                                .equals(attributeValue,
                                        XMLDISABLECOMPONENT_SSO_ALIPAY_AUTHENTICATION_SERVICE) && StringUtils
                                .equals(attributeValue,
                                        XMLDISABLECOMPONENT_SSO_AUTHENTICATION_SERVICE))) {
                    packageLite.disableComponents.add(attributeValue);
                }
                i2++;
            }
            if (i2 < attributeSet.getAttributeCount()) {
                i++;
            } else {
                return;
            }
        }
    }
}
