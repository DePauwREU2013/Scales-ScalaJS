import Base._
import rx._

trait Slide {
  def font: String = "times"
  def fontSize: Int = 35
  def color: Color = Color.Black
  def indicator: Int = 0
}

case class TitleStart(val contents: String) extends Slide {
  override def fontSize = 70
  override def color = Color.RoyalBlue
  override def indicator = 1
}

case class Title(val contents: String) extends Slide {
  override def fontSize = 70
  override def color = Color.RoyalBlue
}

case class SubtitleStart(val contents: String) extends Slide {
  override def fontSize = 50
  override def color = Color.Blue
  override def indicator = 1
}

case class Subtitle(val contents: String) extends Slide {
  override def fontSize = 50
  override def color = Color.Blue
}

case class BulletStart(val contents: String) extends Slide {
  override def indicator = 1
}

case class Bullet(val contents: String) extends Slide {}

case class RegularStart(val contents: String) extends Slide {
  override def indicator = 1
}

case class Regular(val contents: String) extends Slide {}

case class Custom(val contents: String, val f: String, val fs: Int, val c: Color,  val pad: Int = 40, val i: Int = 0) extends Slide {
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
val one_title = "{ Scales IDE }"
val one_desc = "A web based development environment for functional reactive media computation"
val one_author = "Benjamin Kruger and Namchi Do"
val one_author2 = "Brian Howard"
val fontOne = 40

val two_title = "{ Overview }"
val two_pic = Image("http://i.imgur.com/4YNsDWa.png?2", 600, 400)
// val two_pic = Image("http://imgur.com/DRFM4Tn", Canvas.canvas.width / 2, Canvas.canvas.height / 2)


val three_title = "{ Outline }"
val three_one = "Background"
val three_two = "Motivation"
val three_three = "Previous Projects"
val three_four = "Scales IDE"
val three_five = "Demos"
val three_six = "Future Work"
val three_seven = "Acknowledgements"

val four_title= "{ Background }"
val four_one = "What is functional programming?"
val four_two = "... and Scala?"
val four_three = "What's an IDE?"
val four_four = "...and integrating what?"
val four_pic = Image("http://i.imgur.com/LVxRjVi.png", 400,  350)

val five_title = "{ Motivation }"
val five_one = "Teaching functional programming early is good."
val five_two = "Recursive concepts"
val five_three = "A new paradigm"

val six_title = "{ Difficulties }"
val six_one = ". . . revealed in DePauw's Foundations and CS2 courses: "
val six_two = "Unfamiliar syntax"
val six_pic = Image("http://i.imgur.com/H7Sq7Is.png", Canvas.canvas.width, Canvas.canvas.height / 2)
val six_three = "Complicated tool setup"

val seven_title = "{ Previous Projects }"
val seven_one = "Haskell-based projects. . ."

val seven_two = "Scala-based projects"
// val seven_three = "Displaying 'Hello World':"
// val seven_four = Image("http://imgur.com/8ro6s3m", Canvas.canvas.width / 2, Canvas.canvas.height / 4)
// val seven_five = Image("http://imgur.com/857hdQR", Canvas.canvas.width, Canvas.canvas.height / 2)

val eight_title = "{The Scales IDE Project}"
val eight_one = "(1) Scala typed into editor"
val eight_two = "(2) Code sent to server"
val eight_three = "(3) Processed by Scales library"
val eight_four = "(4) Result sent to canvas"
val eight_pic = Image("http://i.imgur.com/8lglrmE.png", Canvas.canvas.width / 2, Canvas.canvas.height / 3)
val nine_title = "{ IDE Demo }"

// val ten_title = "{ Scales.js }"
// val ten_one = "Scala.js"
// val ten_two = "Compiles Scala into JavaScript"
// val ten_three = "Handles most core language features"
// val ten_four = "Adds access to the Web DOM"
// val ten_five = "The Scales library"
// val ten_six = "Media library"
// val ten_seven = "Existing Graphics library"
// val ten_eight = "New Audio library"
// val ten_nine = "New Animation (Anim) library"
// val ten_ten = "New Act library"

// val eleven_title = "{ (Live) Demos }"
// val eleven_pic = Rectangle(50, 100).fill(Color.SeaGreen) beside Ellipse(60, 60).fill(Color.HotPink)
// val eleven_pic2 = Polygon((0, 0), (0, 100), (50, 200), (100, 100)).fill(Color.Violet) on eleven_pic

// val twelve_title = "{ Future Work }"
// val twelve_one = "IDE Enhancements"
// val twelve_two = "Front end work"
// val twelve_three = "Server features"
// val twelve_four = "Library Enhancements"
// val twelve_five = "Reactive"
// val twelve_six = "Audio"

// val thirteen_title = "{ Acknowledgements }"
// val thirteen_one = "NSF & REU"
// val thirteen_two = "DePauw University"
// val thirteen_three = "Dr. Howard"

val text: List[Slide] = List(
    Custom(one_title, "monospace", 80, Color.Black, 150),
    Custom(one_desc, "times", fontOne, Color.Black, 50), 
    Custom(one_author, "times", fontOne, Color.Black),
    Custom(one_author2, "times", fontOne, Color.Black, 0),
    
    TitleStart(two_title),
    Img(two_pic),
    
    TitleStart(three_title),
    Bullet(three_one),
    Bullet(three_two),
    Bullet(three_three),
    Bullet(three_four),
    Bullet(three_five),
    Bullet(three_six),
    Bullet(three_seven),
    
    TitleStart(four_title),
    Bullet(four_three),
    Custom(four_four, "times", 35, Color.Black, 0),
    Bullet(four_one),
    Custom(four_two, "times", 35, Color.Black, 0),
    Img(four_pic),
    
    TitleStart(five_title),
    Regular(five_one),
    Bullet(five_two),
    Bullet(five_three),
    
    TitleStart(seven_title),
    Bullet(seven_one),
    Bullet(seven_two),
    
    TitleStart(six_title),
    Regular(six_one),
    Bullet(six_two),
    Img(six_pic),
    Bullet(six_three),
    
    //TitleStart(seven_title),
    // Custom(seven_three, "times", 35, Color.Black, 40),
    // Img(seven_four),
    // Img(seven_five),

    
    TitleStart(eight_title),
    Img(eight_pic),
    Regular(eight_one),
    Regular(eight_two),
    Regular(eight_three),
    Regular(eight_four),
    
    TitleStart(nine_title),
    
    TitleStart(ten_title),
    Custom(ten_one, "times", 35, Color.Black, 40),
    Bullet(ten_two),
    Bullet(ten_three),
    Bullet(ten_four),
    Custom(ten_five, "times", 35, Color.Black, 40),
    Bullet(ten_six),
    Custom(ten_seven, "times", 35, Color.Black, 40),
    Custom(ten_eight, "times", 35, Color.Black, 40),
    Custom(ten_nine, "times", 35, Color.Black, 40),
    Custom(ten_ten, "times", 35, Color.Black, 40),
    
    TitleStart(eleven_title),
    Img(eleven_pic),
    Img(eleven_pic2),
    
    TitleStart(twelve_title),
    Custom(twelve_one, "times", 35, Color.Black, 40),
    Bullet(twelve_two),
    Bullet(twelve_three),
    Custom(twelve_four, "times", 35, Color.Black, 40),
    Bullet(twelve_five),
    Bullet(twelve_six),
    
    TitleStart(thirteen_title),
    Custom(thirteen_one, "times", 35, Color.Black, 40),
    Custom(thirteen_two, "times", 35, Color.Black, 40),
    Custom(thirteen_three, "times", 35, Color.Black, 40)
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
    currentSlideText() on Rectangle(canvas.width, canvas.height).fill(Color.White).tl
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
      val gg = g.tl.translate((canvas.width - g.bounds.width) / 2, 0)
      val newBounds = RectBounds(gg.bounds.left, gg.bounds.right, gg.bounds.top - 30, gg.bounds.bottom)
      Bounded(gg, newBounds)

    case x: Title =>
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val g = handleTexts(graphics)
      val gg = g.tl.translate((canvas.width - g.bounds.width) / 2, 0)
      val newBounds = RectBounds(gg.bounds.left, gg.bounds.right, gg.bounds.top - 30, gg.bounds.bottom)
      Bounded(gg, newBounds)

    case x: SubtitleStart =>
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val g = handleTexts(graphics)
      val gg = g.tl.translate((canvas.width - g.bounds.width) / 2, 0)
      val newBounds = RectBounds(gg.bounds.left, gg.bounds.right, gg.bounds.top - 40, gg.bounds.bottom)
      Bounded(gg, newBounds)

    case x: Subtitle =>
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val g = handleTexts(graphics)
      val gg = g.tl.translate((canvas.width - g.bounds.width) / 2, 0)
      val newBounds = RectBounds(gg.bounds.left, gg.bounds.right, gg.bounds.top - 40, gg.bounds.bottom)
      Bounded(gg, newBounds)

    case x: BulletStart =>
      val bullet = Rectangle(20, 20).stroke(Color.White).fill(x.color).pad(1.5).tl
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val finalGraphic = handleTexts(graphics)
      val g = bullet beside finalGraphic
      val newBounds = RectBounds(g.bounds.left - 10, g.bounds.right, g.bounds.top - 40, g.bounds.bottom)
      Bounded(g, newBounds)

    case x: Bullet =>
      val bullet = Rectangle(20, 20).stroke(Color.White).fill(x.color).pad(1.5).tl
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val finalGraphic = handleTexts(graphics)
      val g = bullet beside finalGraphic
      val newBounds = RectBounds(g.bounds.left - 10, g.bounds.right, g.bounds.top - 40, g.bounds.bottom)
      Bounded(g, newBounds)

    case x: RegularStart =>
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val g = handleTexts(graphics)
      val newBounds = RectBounds(g.bounds.left, g.bounds.right, g.bounds.top - 40, g.bounds.bottom)
      Bounded(g, newBounds)

    case x: Regular =>
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val g = handleTexts(graphics)
      val newBounds = RectBounds(g.bounds.left, g.bounds.right, g.bounds.top - 40, g.bounds.bottom)
      Bounded(g, newBounds)
      
    case x: Custom =>
      val words = x.contents.split(" ")
      val graphics = splitAll(words, Array[Graphic](), x.font, x.fontSize, x.color)
      val g = handleTexts(graphics)
      val gg = g.translate((canvas.width - g.bounds.width) / 2, 0)
      val newBounds = RectBounds(gg.bounds.left, gg.bounds.right, gg.bounds.top - x.pad, gg.bounds.bottom)
      Bounded(gg, newBounds)
      
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
Act(Anim(canvas.width, canvas.height)(Reactive.KeyPress, getDisplay)).displayOn()