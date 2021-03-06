package edu.depauw.scales.demo

import scala.scalajs.js
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import edu.depauw.scales.reactive._

object PowerPoint {
  import Base._
  import Presentation._

  val currentSlideText: Var[Graphic] = Var(Text(""))
  val history: Var[List[Graphic]] = Var(Nil)
  val index: Var[Int] = Var(-1)
  val canvas = Canvas.canvas

  /*
  ** Usage: Anim(w, h)(Reactive.KeyPress, getDisplay)
  ** getDisplay is the only function that should be called by an outsider
  ** getDisplay figures out what should be shown on the slide based on key presses and manages slide history
  */
  def getDisplay(key: Int): Graphic = {
    if(key == Key.Right || key == Key.PageDown) {
      if(index() < text.length - 1) {
        index() += 1
        history() = history() :+ currentSlideText()

        val tmp: Slide = text(index())
        val g = createSlides(tmp)

        if(tmp.indicator == 1) currentSlideText() = g
        else currentSlideText() = (currentSlideText() above g)
      }
    }
    else if(key == Key.Left || key == Key.PageUp) {
      if(index() > -1) {
        index() -= 1

        currentSlideText() = history().last
        history() = history().init
      }
    }
    currentSlideText()
  }

  /*
  ** decides whether the input slide is a text or graphic type
  */
  private def createSlides(slide: Slide): Graphic = slide match {
    case x: ImgStart => imageHandler(x.contents, "center")
    case x: Img => imageHandler(x.contents, "center")
    case x: ImgLeftStart => imageHandler(x.contents, "left")
    case x: ImgLeft => imageHandler(x.contents, "left")
    case _ => stringToGraphic(slide)
  }

  /*
  ** returns the image with boundary modifications
  */
  private def imageHandler(g: Graphic, s: String): Graphic = s match {
    case "center" =>
      val newBounds = RectBounds(g.bounds.left, g.bounds.right, g.bounds.top - 10, g.bounds.bottom)
      Bounded(g, newBounds).tl.translate((Canvas.canvas.width - g.bounds.width) / 2, 20)
    case "left" =>
      val newBounds = RectBounds(g.bounds.left, g.bounds.right, g.bounds.top - 10, g.bounds.bottom)
      Bounded(g, newBounds).tl
    case _ => 
      Text("")
  }

  /*
  ** takes input text and returns a graphic with text that fits the canvas
  */
  private def stringToGraphic(slide: Slide): Graphic = slide match {
    case x: TitleStart =>
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val g = handleTexts(graphics)
      g.tl.translate((canvas.width - g.bounds.width) / 2, 0)

    case x: Title =>
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val g = handleTexts(graphics)
      g.tl.translate((canvas.width - g.bounds.width) / 2, 0)

    case x: SubtitleStart =>
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val g = handleTexts(graphics)
      g.tl.translate((canvas.width - g.bounds.width) / 2, 0)

    case x: Subtitle =>
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val g = handleTexts(graphics)
      g.tl.translate((canvas.width - g.bounds.width) / 2, 0)

    case x: BulletStart =>
      val bullet = Rectangle(10, 10).pad(1.5).tl
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val finalGraphic = handleTexts(graphics)
      val g = bullet beside finalGraphic
      val newBounds = RectBounds(g.bounds.left, g.bounds.right, g.bounds.top - 10, g.bounds.bottom)
      Bounded(g, newBounds)

    case x: Bullet =>
      val bullet = Rectangle(10, 10).pad(1.5).tl
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val finalGraphic = handleTexts(graphics)
      val g = bullet beside finalGraphic
      val newBounds = RectBounds(g.bounds.left, g.bounds.right, g.bounds.top - 10, g.bounds.bottom)
      Bounded(g, newBounds)

    case x: RegularStart =>
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      handleTexts(graphics)

    case x: Regular =>
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      handleTexts(graphics)

    case x: Custom =>
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      handleTexts(graphics)

    case _ => Text("")
  }

  /*
  ** stacks the graphical texts on top of each other so that they fit inside the canvas
  */
  private def handleTexts(gs: Array[Graphic]): Graphic = gs.length match {
    case 1 => gs(0).tl
    case _ => gs(0).tl above handleTexts(gs.tail)
  }

  /*
  ** splits the words to fit inside canvas
  */
  private def splitAll(words: Array[String], graphics: Array[Graphic], f: String, fs: Int, c: Base.Color): Array[Graphic] = words.length match {
    case 0 => graphics
    case _ =>
      val (g, rest) = takeWords(words, Text(""), f, fs, c)
      splitAll(rest, graphics :+ g, f, fs, c)
  }

  /*
  ** helper for splitAll
  */
  private def takeWords(words: Array[String], graphic: Graphic, f: String, fs: Int, c: Base.Color): (Graphic, Array[String]) = words.length match {
    case 0 => (graphic, words)
    case _ =>
      val newGraphic = graphic beside Text(" " + words(0), Font(f, fs), false).fill(c)
      if(newGraphic.bounds.width < canvas.width - 40) return takeWords(words.tail, newGraphic, f, fs, c)
      else return (graphic, words)
  }

}