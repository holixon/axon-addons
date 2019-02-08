package io.toolisticon.axon.addons.jgiven

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.Quoted
import com.tngtech.jgiven.junit5.SimpleScenarioTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

open class DummyTest : SimpleScenarioTest<DummySteps>() {

  @Test
  fun `something happens`() {
    given()
      .a_foo("hello")

    `when`()
      .add_bar("world")

    then()
      .`it is $`("hello world")
  }
}


open class DummySteps : Stage<DummySteps>() {

  lateinit var foo:String

  open fun a_foo(name:String) = self() .apply {
    foo = name
  }

  open fun add_bar(name: String) = self().apply {
    foo += " $name"
  }


  open fun `it is $`(@Quoted exp:String) = self().apply {
    Assertions.assertEquals(exp, foo)
  }
}
