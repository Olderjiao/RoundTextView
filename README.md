# 圆角渐变色边框按钮

<img src="C:\Users\Administrator\Desktop\1612163539111.gif" alt="1612163539111" style="zoom: 33%;" />



> 因为项目需要只有边框和文字渐变效果得按钮，尝试过用drawable文件设置，发现只能设置全背景渐变效果，也懒得用其他方式，就自己动手画了一个，画完之后就把全背竟的也给加上了，因为这个项目类似这种的按钮还不少，避免了挨个去创建drawable文件了，为了防止以后还会用到，就顺便学习了一下依赖，给这个库生成了一个依赖。



## 外部gradle导入：

```java
repositories { 
    maven { url 'https://jitpack.io' } 
} 
```



## 项目gradle导入：

```java
implementation 'com.github.Olderjiao:RoundTextView:1.0'
```



## 自定义属性注释解析

|        属性名称        |     属性解释     |
| :--------------------: | :--------------: |
| m_gradient_color_start |     起始颜色     |
|  m_gradient_color_end  |     结束颜色     |
|     m_stroke_width     |     线条宽度     |
|       m_is_full        | 是否渐变背景充满 |



## demo布局代码

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <com.ltb.myroundtextlibrary.widget.MyRoundTextView
        android:id="@+id/btn1"
        android:layout_width="300dp"
        android:layout_height="40dp"
        android:layout_centerInParent="true"
        android:layout_gravity="center"
        android:layout_marginLeft="30dp"
        android:layout_marginTop="30dp"
        android:layout_marginRight="30dp"
        android:text="注册"
        app:m_gradient_color_end="@color/colorEnd"
        app:m_gradient_color_start="@color/colorStart" />

    <com.ltb.myroundtextlibrary.widget.MyRoundTextView
        android:id="@+id/btn2"
        android:layout_width="300dp"
        android:layout_height="40dp"
        android:layout_centerInParent="true"
        android:layout_gravity="center"
        android:layout_marginLeft="30dp"
        android:layout_marginTop="20dp"
        android:layout_marginRight="30dp"
        android:gravity="center"
        android:text="确认"
        app:m_gradient_color_end="@color/colorEnd"
        app:m_gradient_color_start="@color/colorStart"
        app:m_is_full="true" />

</LinearLayout>
```



## Activity代码

```java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final MyRoundTextView myRoundTextView = findViewById(R.id.btn2);
        myRoundTextView.setContent("确认", "取消");
        myRoundTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRoundTextView.setFull(!myRoundTextView.isFull());
            }
        });

    }
```



**本人属于刚入行的安卓，控件可能还有很多问题，欢迎各位大佬指正。**

**共同开发，共同进步，谢谢各位！！！**













