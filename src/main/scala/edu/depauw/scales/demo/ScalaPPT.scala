import rx._
import Base._

trait Slide {
  def font: String = "serif"
  def fontSize: Int = 20
  def color: Color = Color.Black
  def indicator: Int = 0
}

case class TitleStart(val contents: String) extends Slide {
  override def fontSize = 48
  override def color = Color.SeaGreen
  override def indicator = 1
}

case class Title(val contents: String) extends Slide {
  override def fontSize = 48
  override def color = Color.SeaGreen
}

case class SubtitleStart(val contents: String) extends Slide {
  override def fontSize = 30
  override def color = Color.SeaGreen
  override def indicator = 1
}

case class Subtitle(val contents: String) extends Slide {
  override def fontSize = 30
  override def color = Color.HotPink
}

case class BulletStart(val contents: String) extends Slide {
  override def indicator = 1
}

case class Bullet(val contents: String) extends Slide {}

case class RegularStart(val contents: String) extends Slide {
  override def indicator = 1
}

case class Regular(val contents: String) extends Slide {}

case class Custom(val contents: String, val f: String, val fs: Int, val c: Color, val i: Int = 0) extends Slide {
  override def font = f
  override def fontSize = fs
  override def color = c
  override def indicator = i
}

case class ImgStart(val contents: Graphic) extends Slide {
  override val indicator = 1
}

case class Img(val contents: Graphic) extends Slide {}

case class ImgLeftStart(val contents: Graphic) extends Slide {
  override val indicator = 1
}

case class ImgLeft(val contents: Graphic) extends Slide {}

//presentation
val title1 = "Title of Slide #1"
val title2 = "Title of Slide #2"
val title3 = "Title of Slide #3"
val title4 = "Title of Slide #4"
  
val subtitle1 = "Subtitle #1: By Anonymous x 3"
val subtitle2 = "Subtitle #2"
val subtitle3 = "Subtitle #3"
val subtitle4 = "Subtitle #4"

val lorem1 = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " + 
    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "+
    "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
    "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

val lorem2 = "Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. " +
    "Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem." + 
    " Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. " +
    "Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum."   

val lorem3 = "Maecenas placerat velit sit amet libero scelerisque, ut luctus odio consectetur."

val lorem4 = "Praesent tempor ante vitae nisi viverra, vel rutrum sem molestie."

val lorem5 = "Integer id ante malesuada, consectetur dolor et, rutrum est.Maecenas at odio luctus, commodo felis eu, molestie lectus."

val lorem6 = "Nulla tincidunt justo eget enim lacinia aliquet. " + 
    "Quisque eu nulla et mauris eleifend ullamcorper. Integer bibendum sapien interdum quam blandit, sed eleifend libero scelerisque. " +
    "Vestibulum pretium nibh quis diam auctor, eget tincidunt ipsum imperdiet. Cras semper quam et varius cursus."

val lorem7 = "Donec at justo consequat, dictum nibh quis, fringilla turpis."

val lorem8 = "Lorem ipsum"

val lorem9 = "Fusce a facilisis augue. Duis egestas odio a vulputate consectetur. " + 
    "Morbi sagittis molestie urna et vehicula. Nam ornare mauris quis risus faucibus, ut pharetra tortor hendrerit. " + 
    "Praesent eu sollicitudin ante. Vestibulum nec varius sem. Cras mi nibh, interdum non lobortis eu, blandit euismod tellus. " 

val graphic1 = Rectangle(100, 100).fill(Color.RoyalBlue)

val graphic2 = Rectangle(100, 100).fill(Color.Yellow) beside Ellipse(100, 100).fill(Color.HotPink)

val graphic3 = Image("https://tse4.mm.bing.net/th?id=HN.608021731299754631&pid=1.7", 400, 300)

val text: List[Slide] = List(
    TitleStart(title1),
    Subtitle(subtitle1),
    Bullet(lorem1),
    Bullet(lorem2),
    TitleStart(title2),
    Subtitle(subtitle2),
    Bullet(lorem3),
    Bullet(lorem4),
    Bullet(lorem5),
    TitleStart(title3),
    Subtitle(subtitle3),
    Bullet(lorem6),
    Bullet(lorem7),
    Bullet(lorem8),
    TitleStart(title4),
    Subtitle(subtitle4),
    Custom(lorem9, "monospace", 12, Color.RoyalBlue),
    Img(graphic1),
    ImgLeft(graphic2),
    ImgStart(graphic3)
)

//powerpoint
val currentSlideText: Var[Graphic] = Var(Text(""))
val history: Var[List[Graphic]] = Var(Nil)
val index: Var[Int] = Var(-1)
val canvas = Canvas.canvas

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

def createSlides(slide: Slide): Graphic = slide match {
    case x: ImgStart => imageHandler(x.contents, "center")
    case x: Img => imageHandler(x.contents, "center")
    case x: ImgLeftStart => imageHandler(x.contents, "left")
    case x: ImgLeft => imageHandler(x.contents, "left")
    case _ => stringToGraphic(slide)
}

def imageHandler(g: Graphic, s: String): Graphic = s match {
    case "center" =>
      val newBounds = RectBounds(g.bounds.left, g.bounds.right, g.bounds.top - 10, g.bounds.bottom)
      Bounded(g, newBounds).tl.translate((Canvas.canvas.width - g.bounds.width) / 2, 20)
    case "left" =>
      val newBounds = RectBounds(g.bounds.left, g.bounds.right, g.bounds.top - 10, g.bounds.bottom)
      Bounded(g, newBounds).tl
    case _ => 
      Text("")
}

def stringToGraphic(slide: Slide): Graphic = slide match {
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

def handleTexts(gs: Array[Graphic]): Graphic = gs.length match {
    case 1 => gs(0).tl
    case _ => gs(0).tl above handleTexts(gs.tail)
}

def splitAll(words: Array[String], graphics: Array[Graphic], f: String, fs: Int, c: Base.Color): Array[Graphic] = words.length match {
    case 0 => graphics
    case _ =>
      val (g, rest) = takeWords(words, Text(""), f, fs, c)
      splitAll(rest, graphics :+ g, f, fs, c)
}

def takeWords(words: Array[String], graphic: Graphic, f: String, fs: Int, c: Base.Color): (Graphic, Array[String]) = words.length match {
    case 0 => (graphic, words)
    case _ =>
      val newGraphic = graphic beside Text(" " + words(0), Font(f, fs), false).fill(c)
      if(newGraphic.bounds.width < canvas.width - 40) return takeWords(words.tail, newGraphic, f, fs, c)
      else return (graphic, words)
}

//testing
//Ellipse(40, 40).fill(Color.Orange).displayOn()
Anim(canvas.width, canvas.height)(Reactive.KeyPress, getDisplay).act()