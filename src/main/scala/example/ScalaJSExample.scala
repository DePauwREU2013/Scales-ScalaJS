package example

import scala.scalajs.js
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import edu.depauw.scales.reactive._
import edu.depauw.scales.music._
import edu.depauw.scales.demo._
import edu.depauw.scales.act._

object window extends js.Object {
  val innerHeight: Int = ???
}

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    val playground = dom.document.getElementById("playground")
    val canvas = dom.document.getElementById("output").asInstanceOf[dom.HTMLCanvasElement]
    canvas.width = 800
    canvas.height = 600

    val paragraph = dom.document.createElement("p")
    paragraph.innerHTML = "<strong>It works! Window height = " + window.innerHeight + ". Using Dynamic, that's " + js.Dynamic.global.window.innerHeight + ".</strong>"
    playground.appendChild(paragraph)

    /*-----------------------------------------*/
    import Base._
    import Reactive._
    
    /*------------------ACT DEMO------------------*/
    // import ActDemo._
    // actor2.act()

    /*------------------ACT DISPLAYON DEMO------------------*/
    val circleSquare = Act(Ellipse(50, 50).fill(Color.HotPink)) beside Act(Rectangle(50, 50).fill(Color.SeaGreen))
    circleSquare.displayOn()

    /*------------------POWERPOINT DEMO------------------*/
    // import PowerPoint._
    // Anim(KeyPress, getDisplay).act()


    /*------------------PRETTY PICTURE DEMO------------------*/
    // import PrettyPicture._
    // actor.act()

    /*------------------THE DR.HOWARD DEMO------------------*/

  } 

}