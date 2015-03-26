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

		XmlResourceParser openXmlResourceParser = null;
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

						if (openXmlResourceParser != null) {
							openXmlResourceParser.close();
						}
						throw th;
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
			if (openXmlResourceParser != null) {
				openXmlResourceParser.close();
			}

		}
		return null;
	}

	protected static android.taobao.atlas.runtime.PackageLite parse(
			android.content.res.XmlResourceParser xmlResourceParser)
					throws java.lang.Exception {

		int v2;
		// PackageLite v0_1;
		int index;
		final int endTag = XmlPullParser.END_TAG;
		final   int startTag = XmlPullParser.START_TAG;

		PackageLite mPackageLite = new PackageLite();
		do {
			index = xmlResourceParser.next();
			if (index == startTag) {
				break;
			}
		} while (index != XmlPullParser.END_DOCUMENT);

		if (index != startTag) {
			PackageLite.log.error("No start tag found");
			mPackageLite=null;
		} else if (!xmlResourceParser.getName().equals("manifest")) {
			PackageLite.log.error("No <manifest> tag");
			mPackageLite=null;
		} else {
			mPackageLite.packageName = ((AttributeSet) xmlResourceParser).getAttributeValue(null,
					"package");
			if (mPackageLite.packageName != null && mPackageLite.packageName.length() != 0) {
				index = 0;
				v2 = 0;
			} else {
				PackageLite.log.error("<manifest> does not specify package");
				return null;
			}

			while (index < ((AttributeSet) xmlResourceParser).getAttributeCount()) {
				String value = ((AttributeSet) xmlResourceParser).getAttributeName(index);
				if (value.equals("versionCode")) {
					mPackageLite.versionCode = ((AttributeSet) xmlResourceParser)
							.getAttributeIntValue(index, 0);
					++v2;
				} else if (value.equals("versionName")) {
					mPackageLite.versionName = ((AttributeSet) xmlResourceParser)
							.getAttributeValue(index);
					++v2;
				}

				if (v2 >= startTag) {
					break;
				}

				++index;
			}

			index = xmlResourceParser.getDepth() + 1;
			while (true) {
				int v1 = xmlResourceParser.next();
				System.out.println(xmlResourceParser.getName());
				if (v1 != XmlPullParser.END_DOCUMENT) {
					if (xmlResourceParser.getName().equals("application")) {
						if (!PackageLite
								.parseApplication(mPackageLite, ((XmlPullParser) xmlResourceParser),
										((AttributeSet) xmlResourceParser))) {
							return null;
						}

						return mPackageLite;
					}

					if (v1 == endTag && xmlResourceParser.getDepth() < index) {
						break;
					}

					if (v1 == endTag) {
						continue;
					}

					if (v1 == 4) {
						continue;
					}

					PackageLite.skipCurrentTag(((XmlPullParser) xmlResourceParser));
					continue;
				}

				break;
			}


		}

		return mPackageLite;

	}



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
		
		

		final int innerDepth = xmlPullParser.getDepth();

		int type;
		while ((type = xmlPullParser.next()) != XmlPullParser.END_DOCUMENT
				&& (type != XmlPullParser.END_TAG || xmlPullParser.getDepth() > innerDepth)) {
			if (type == XmlPullParser.END_TAG || type == XmlPullParser.TEXT) {
				continue;
			}

			String tagName = xmlPullParser.getName();
			if (tagName.equals("activity")) {

				parseComponentData(packageLite, xmlPullParser,
						attributeSet, false);
			
			} else if (tagName.equals("receiver")) {

				parseComponentData(packageLite, xmlPullParser,
						attributeSet, true);
			
			} else if (tagName.equals("service")) {

				parseComponentData(packageLite, xmlPullParser,
						attributeSet, true);
			
			} else if (tagName.equals("provider")) {

				parseComponentData(packageLite, xmlPullParser,
						attributeSet, false);
			
			} else if (tagName.equals("activity-alias")) {
			} else if (xmlPullParser.getName().equals("meta-data")) {

				packageLite.metaData = parseMetaData(xmlPullParser,
						attributeSet, packageLite.metaData);
			
				
			} else if (tagName.equals("uses-library")) {
			} else if (tagName.equals("uses-package")) {
			} else {
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
			if (next == XmlPullParser.END_DOCUMENT) {
				return;
			}
			if (next == XmlPullParser.END_TAG && xmlPullParser.getDepth() <= depth) {
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
					attributeValue = str.concat(attributeValue);
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
