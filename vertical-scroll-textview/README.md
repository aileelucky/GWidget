# 主要类：
## PortalManiActivity  
门户主页面，项目中，首页继承这个页面，就可以使用门户功能，这个页面包含了UI和首页底部菜单的逻辑。底部菜单目前就有一个门户页面，其它页面（类似"我的"、"消息"等），可以自由扩展，页面最多显示五个菜单，多的需要配置出"更多"按钮，可以把多的页面收纳。

## PortalConfig
用来配置一些初始化参数,建议在MainActivity初始化  
| 参数 | 描述 |必须|
|---|------------|---|
|TabConfig|首页导航配置，在这里扩展首页底部菜单，包含类型和页面|否|
|envUrl|当前环境地址:<br>切换环境要记得更新，主要用来拼接图片、文件的url<br>因为安全问题，图片的链接可能不带域名|是|
|ComponentConfig|这个类可以扩展自定义的门户组件|否|
|IAccountManager|主项目中需要实现的(一般取值都是对应AccountManager中的方法)|是|
|ICommonManager|用来处理一些通用的接口,项目中要实现|是|

## PortalMainAction
集成PortalMainActivity后，需要实现的方法，并不是全部需要实现，
| 参数 | 描述 |必须|
|---|------------|---|
|logout()|退出登录|是|
|initMessageCount()|处理消息未读数|否|
|setContactsTabDot()|通讯录提示|否|
|isForeground()|是否在前台|是|
|getDrawerFragment()|侧滑出来的“我的”页面|否|
|showContactsTip()|通讯录保护提示，参考基线`AccountUtils`中`showDialogTips`|否|

## IAccountManager
项目中需要需要实现这个方法

```java
public interface ICommonManager {
    // CommonRedirectActivity.start()
    void commonRedirectActivityStart(Context context, String uri); 
    //shinemo中HomePageUtils.checkCalendarPermission()
    void checkCalendarPermission(Activity activity, List<AppPortalElementVo> mModulelist);
    //shinemo中HomePageUtils.getWeather()
    void getWeather(Activity activity, CompositeDisposable mCompositeSubscription, TextView tvWeather);  
    // AppCommonUtils中的getBrandColor
    String getBrandColor(Context context);
    //OutsideActivity.startActivity
    void startOutSideActivity(Context context, int tab); 
}
```
## ICommonManager
通用的接口

## 集成步骤
在Application中调用PortalConfig.init(Application application, IAccountManager iAccountManager, ICommonManager iCommon)

EventOrgChange 要使用SDK中的

