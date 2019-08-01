package com.example.print.template.jmx.manage;

import javax.management.*;
import javax.management.loading.ClassLoaderRepository;
import java.text.DateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

/*
 *@Author BieFeNg
 *@Date 2019/8/1 14:44
 *@DESC
 */
public class ManageMBeanInfo {

    private MBeanAttributeInfo[] attinfo = null;
    private MBeanInfo mBeanInfo;
    private String[] propertyName = null;
    private MBeanServer server;
    private String[] propertyView = null;

    public ManageMBeanInfo(MBeanServer server, String name) {
        try {
            this.server = server;
            ObjectName helloName = new ObjectName(name);
            Set<ObjectName> objectNames = server.queryNames(helloName, null);
            ObjectName objectName = null;
            if (null == objectNames || objectNames.size() <= 0) {
                return;
            } else {
                objectName = (ObjectName) objectNames.toArray()[0];
            }
            if (null != objectName) {
                mBeanInfo = server.getMBeanInfo(objectName);
            }
            attinfo = checkedAttributeInfo(mBeanInfo.getAttributes());
            this.propertyName = new String[this.attinfo.length];
            this.propertyView = new String[this.attinfo.length];

            int j;
            for (j = this.propertyName.length - 1; j >= 0; --j) {
                this.propertyName[j] = this.attinfo[j].getName();
            }
            this.quicksort(this.propertyName, 0, this.propertyName.length - 1);
            for (j = this.propertyName.length - 1; j >= 0; --j) {
                this.propertyView[j] = this.getStringValue(objectName, this.attinfo[j]);
            }
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        }

    }

    private MBeanAttributeInfo[] checkedAttributeInfo(MBeanAttributeInfo[] orig) {
        if (orig == null) {
            return null;
        } else {
            MBeanAttributeInfo[] attr = new MBeanAttributeInfo[orig.length];

            for (int i = 0; i < attr.length; ++i) {
                if (orig[i] != null) {
                    attr[i] = orig[i];
                } else {
                    attr[i] = new MBeanAttributeInfo("null_MBeanAttributeInfo", (String) null, "The attribute info at index[" + i + "] is null. Please" + " check the MBeanInfo.", true, false, false);
                }
            }

            return attr;
        }
    }

    private String getStringValue(ObjectName o, MBeanAttributeInfo attrInfo) {
        Object result = null;
        boolean r = attrInfo.isReadable();
        try {
            if (r) {
                result = this.server.getAttribute(o, attrInfo.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result instanceof Date) {
            DateFormat df = DateFormat.getDateTimeInstance(1, 1);
            df.setTimeZone(TimeZone.getDefault());
            return df.format((Date) result);
        } else {
            return result == null ? null : result.toString();
        }
    }

    private Class loadClass(String className) throws ClassNotFoundException {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException var4) {
            ClassLoaderRepository clr = MBeanServerFactory.getClassLoaderRepository(this.server);
            if (clr == null) {
                throw new ClassNotFoundException(className);
            } else {
                return clr.loadClass(className);
            }
        }
    }

    protected void quicksort(String[] v, int lo0, int hi0) {
        int lo = lo0;
        int hi = hi0;
        if (hi0 > lo0) {
            String mid = v[(lo0 + hi0) / 2];

            while (lo <= hi) {
                while (lo < hi0 && this.lt(v[lo], mid)) {
                    ++lo;
                }

                while (hi > lo0 && this.lt(mid, v[hi])) {
                    --hi;
                }

                if (lo <= hi) {
                    this.inverseS(v, lo, hi);
                    ++lo;
                    --hi;
                }
            }

            if (lo0 < hi) {
                this.quicksort(v, lo0, hi);
            }

            if (lo < hi0) {
                this.quicksort(v, lo, hi0);
            }
        }

    }

    protected boolean lt(String a, String b) {
        if (a == null) {
            return false;
        } else if (b == null) {
            return true;
        } else {
            return a.compareTo(b) < 0;
        }
    }

    protected void inverseS(String[] a, int lo, int hi) {
        String T = a[lo];
        a[lo] = a[hi];
        a[hi] = T;
    }
}
