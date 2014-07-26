package example

import scala.scalajs.js
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import edu.depauw.scales.reactive._
import edu.depauw.scales.music._

trait FontType {
  val font: String = "serif"
  val fontSize: Int
  val color: Base.Color
}
case object Title extends FontType { 
  val fontSize = 48
  val color = Base.Color.HotPink
}
case object Subtitle extends FontType {
  val fontSize = 30
  val color = Base.Color.SeaGreen
}
case object RegularBullet extends FontType {
  val fontSize = 20
  val color = Base.Color.Black
}
case object Regular extends FontType {
  val fontSize = 20
  val color = Base.Color.Black
}
case class Custom(val f: String, val fontSize: Int, val color: Base.Color) extends FontType {
  override val font = f
}

object window extends js.Object {
  val innerHeight: Int = ???
}

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    val playground = dom.document.getElementById("playground")

    val paragraph = dom.document.createElement("p")
    paragraph.innerHTML = "<strong>It works! Window height = " + window.innerHeight + ". Using Dynamic, that's " + js.Dynamic.global.window.innerHeight + ".</strong>"
    playground.appendChild(paragraph)

    ///---------------///
    import Base._
    import Reactive._

    def fnMouse(xy: (Int, Int)): Graphic = {
      Shape(180, 180) {
        (0.5, 0.5) lineTo
        (0, 0) lineTo
        (1, 0) lineTo
        (1, 0.5) curveTo
        (0.5, 1) lineTo
        (0, 1)
      }.strokeWidth(3).fill(Color.HotPink).stroke(Color.Aqua).translate(xy._1, xy._2)
    }

    def fnTime(t: Double): Graphic = {
      (Ellipse(50,75).fill(Color.Blue) on Ellipse(75, 75).fill(Color.SeaGreen)).translate(t * 30, 100)
    }

    def fnTime2(t: Double): Graphic = {
      (Rectangle(80, 50).fill(Color.Lilac).stroke(Color.Red) beside Ellipse(50, 80).fill(Color.Mustard).rotate(20 deg)).translate(100, t * 50)
    }

    def fnNotes(xy: (Int, Int)): ScalesNote = {
      (B after A)//(A.>(4) after B) par (C.<(1) after D.>>>>)
    }

    // Reactor(MouseClick, fnNotes)
    // Reactor(MouseClick, fnMouse)
    // Reactor(ClockTick(1, 20), fnTime)
    // Reactor(ClockTick(5, 20), fnTime2)
    //Reactor(MouseClickX, fnSounds)








  def takeWords(words: Array[String], graphic: Graphic, font: FontType): (Graphic, Array[String]) = words.length match {
    case 0 => (graphic, words)
    case _ =>
      val newGraphic = graphic beside Text(" " + words(0), Font(font.font, font.fontSize), false).fill(font.color)
      if(newGraphic.bounds.width < Canvas.canvas.width - 40) return takeWords(words.tail, newGraphic, font)
      else return (graphic, words)
  }

  // for splitting text so that it fits on canvas
  def splitAll(words: Array[String], graphics: Array[Graphic], font: FontType): Array[Graphic] = words.length match {
    case 0 => graphics
    case _ =>
      val (g, rest) = takeWords(words, Text(""), font)
      splitAll(rest, graphics :+ g, font)
  }

  def handleTexts(texts: Array[Graphic]): Graphic = texts.length match {
    case 1 => texts(0).tl
    case _ => texts(0).tl above handleTexts(texts.tail)
  }

  val loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " + 
    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "+
    "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
    "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

  val lorem2 = "Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. " +
    "Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem." + 
    " Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. " +
    "Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum."   





      def stringToGraphic(text: String, font: FontType = RegularBullet): Graphic = font match {
      case Title =>
        val arr = text.split(" ")
        val graphics = splitAll(arr, Array[Graphic](), Title)
        val g = handleTexts(graphics)

        //val g = Text(text, Font(font.font, font.fontSize), false).fill(font.color)
        val gPrime = g.tl.translate((Canvas.canvas.width - g.bounds.width) / 2, 0)
        gPrime
      case Subtitle =>
        val arr = text.split(" ")
        val graphics = splitAll(arr, Array[Graphic](), Subtitle)
        val g = handleTexts(graphics)

        //val g = Text(text, Font(font.font, font.fontSize), false).fill(font.color)
        val gPrime = g.tl.translate((Canvas.canvas.width - g.bounds.width) / 2, 0)
        gPrime
      case RegularBullet =>
        val bullet = Rectangle(10, 10).pad(1.5).tl 
        val arr = text.split(" ")
        val graphics = splitAll(arr, Array[Graphic](), RegularBullet)
        val finalGraphic = handleTexts(graphics)
        val g = bullet beside finalGraphic
        val newBounds = RectBounds(g.bounds.left, g.bounds.right, g.bounds.top - 10, g.bounds.bottom)
        Bounded(g, newBounds)
      case Regular =>
        val arr = text.split(" ")
        val graphics = splitAll(arr, Array[Graphic](), Regular)
        handleTexts(graphics)
      case Custom(_, _, _) =>
        val arr = text.split(" ")
        val graphics = splitAll(arr, Array[Graphic](), font)
        handleTexts(graphics)
    }


    val title1 = "Presentation: SCALES IDE"
    val subtitle1 = "By Anonymous x 3"

    // val text1 = stringToGraphic(title1, Title)
    // val text2 = stringToGraphic(subtitle1, Subtitle)
    // val texts = (text1 above text2)//.render(Canvas.ctx)

    // val text3 = stringToGraphic(loremIpsum, RegularBullet)
    // val text4 = stringToGraphic(lorem2, RegularBullet)
    // ((texts above text3) above text4).render(Canvas.ctx)








  //todo: put texts in an array or list and get each one out individually to turn into graphics
  //var currentSlideGraphics(), and know when to clear graphics
  //respond to clicks

  val slideStuff: List[(Int, FontType, String)] = List(
    (1, Title, title1),
    (0, Subtitle, subtitle1),
    (0, RegularBullet, loremIpsum),
    (0, RegularBullet, lorem2)
  )

  def display(index: Int): Graphic = {
    val current = slideStuff(index)
    //paragraph.innerHTML += current._3
    stringToGraphic(current._3, current._2)
  }

  val g = display(3)
  g.render(Canvas.ctx)





  }

}
