# GWidget 

***用途*** 用来维护自定义的view  

## VerticalScrollTextView
<pre><code>compile 'com.china.gu:vertical-scroll-textview:1.0.0'</code></pre>
#### 使用方式 ####
* 设置数据<pre><code>public void setList(ArrayList<Hot> list) {
            hotList.clear();
            hotList.addAll(list);
            startPosition = 0;
            stop();//这样可以兼容更新数据
            start();
    }
</code></pre>
* 停止滚动<pre><code>public void stop() {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
        }
     } </code></pre>
