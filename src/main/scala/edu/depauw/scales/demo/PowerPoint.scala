package edu.depauw.scales.demo

import scala.scalajs.js
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import edu.depauw.scales.reactive._

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

case class Custom(override val font: String, val fontSize: Int, val color: Base.Color) extends FontType {}

case object ImageCentered extends FontType {
  val fontSize = 0
  val color = Base.Color.Clear
}

case object ImageLeft extends FontType {
  val fontSize = 0
  val color = Base.Color.Clear
}


object PowerPoint {

  import Presentation._
  
  def takeWords(words: Array[String], graphic: Graphic, font: FontType): (Graphic, Array[String]) = words.length match {
    case 0 => (graphic, words)
    case _ =>
      val newGraphic = graphic beside Text(" " + words(0), Font(font.font, font.fontSize), false).fill(font.color)
      if(newGraphic.bounds.width < Canvas.canvas.width - 40) return takeWords(words.tail, newGraphic, font)
      else return (graphic, words)
  }

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

  def stringToGraphic(text: String, font: FontType = RegularBullet): Graphic = font match {
    case Title =>
      val arr = text.split(" ")
      val graphics = splitAll(arr, Array[Graphic](), Title)
      val g = handleTexts(graphics)
      g.tl.translate((Canvas.canvas.width - g.bounds.width) / 2, 0)
    case Subtitle =>
      val arr = text.split(" ")
      val graphics = splitAll(arr, Array[Graphic](), Subtitle)
      val g = handleTexts(graphics)
      g.tl.translate((Canvas.canvas.width - g.bounds.width) / 2, 0)
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

  def createSlides[T](info: T, font: FontType = RegularBullet): Graphic = font match {
    case ImageCentered =>
      info match {
        case x: Graphic => 
          val newBounds = RectBounds(x.bounds.left, x.bounds.right, x.bounds.top - 10, x.bounds.bottom)
          Bounded(x, newBounds).tl.translate((Canvas.canvas.width - x.bounds.width) / 2, 20)
        case _ => Text("")
      }
    case ImageLeft =>
      info match {
        case x: Graphic => 
          val newBounds = RectBounds(x.bounds.left, x.bounds.right, x.bounds.top - 10, x.bounds.bottom)
          Bounded(x, newBounds).tl
      }
    case _ =>
      info match {
        case x: String => stringToGraphic(info.asInstanceOf[String], font)
        case _ => Text("")
      }
  }

  val currentSlideText: Var[Graphic] = Var(Text(""))
  val history: Var[List[Graphic]] = Var(Nil) 
  val index: Var[Int] = Var(-1)

  import Base._
  def getDisplay(key: Int): Graphic = {
    if(key == Key.Right) {
      if(index() < text.length - 1) {
        index() += 1
        history() = history() :+ currentSlideText()

        val (indicator, font, txt) = text(index())
        val g = createSlides(txt, font)
        if(indicator == 1) currentSlideText() = g
        else currentSlideText() = (currentSlideText() above g)
      }
    }
    else if(key == Key.Left) {
      if(index() > 0) {
        index() -= 1

        currentSlideText() = history().last
        history() = history().init
      } 
    }
    currentSlideText()
  }

}