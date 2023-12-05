# StoryView

A project designed to showcase an Instagram-inspired Story View component, elevating user experiences by integrating a visually dynamic feature. It seamlessly supports to both images and videos, providing an engaging platform that captivates users with its immersive storytelling interface.

## Download

To add a dependency using Gradle:

``` 
implementation("com.github.Deepika-Attri:ViewMyStories:1.0.6")

```

## Library projects

Add in Gradle:

```
buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }
    }
}

```

## How to use?

Add in xml:

```
<com.storyview.StoryProgressView
            android:id="@+id/storyProgressView"
            android:layout_width="match_parent"
            android:layout_height="3dp"
            android:layout_gravity="top"
            android:layout_marginTop="8dp"
            android:paddingLeft="8dp"
            android:paddingRight="8dp"
            app:progressBackgroundColor="@color/grey"
            app:progressColor="@color/white" />

```
