package com.jeff.covidtracker

class TestApplication: MyApplication() {

    private lateinit var component: TestComponent

    override fun initializeComponent(): AppComponent {
          component = DaggerTestComponent.builder().application(this).build()
          return component
    }

    fun getComponent(): TestComponent = component
}