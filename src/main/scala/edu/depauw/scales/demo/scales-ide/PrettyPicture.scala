import Reactive._

//mouse click changes
val canvas = Canvas.canvas

canvas.width = 700
canvas.height = 600

	val w = Canvas.canvas.width
	val h = Canvas.canvas.height / 2
	def fnBackground(x: Double): Graphic = {
		lazy val day = Rectangle(w, h).stroke(Color.Clear).strokeWidth(0).fill(Color.SkyBlue).tl
		lazy val night = Rectangle(w, h).stroke(Color.Clear).fill(Color.NavyBlue).tl
		lazy val grass = Rectangle(w, h).stroke(Color.Clear).fill(Color.Green).tl
		if(x % 2 == 0) {
			(day above grass)
		} else (night above grass)
	}

//clock ticks
	def fnCar(x: Double): Graphic = {
		val wheel1 = Ellipse(50, 50).fill(Color.Grey)
		val carBody = Polygon((0, 100), (75, 0), (150, 0), (200, 50), (250, 50), (250, 100)).strokeWidth(4).fill(Color.HotPink)
		
		val graphic = wheel1.bottom on (wheel1.center on carBody.bl).br
		//dom.alert("graphic width: " + graphic.bounds.width)
		val xPos = (x * 30) % (Canvas.canvas.width + graphic.bounds.width)
		graphic.translate(xPos, 350)
	}

//mouse position
	def fnButterfly(xy: (Int, Int)): Graphic = {
		val butterfly = Polygon(
			(0, 0), (25, 15), (30, 30),
			(28, 0), (32, 0), (35, 30), (38, 0),
			(42, 0), (40, 30), (45, 15),
			(70, 0), (60, 15), (65, 25),
			(55, 30), (45, 40), (55, 45),
			(65, 55), (55, 60), (45, 55),
			(35, 45), 
			(25, 55), (10, 60), (5, 55), (10, 45), (25, 40), (15, 30), (5, 25), (10, 15)
		).fill(Color.Violet)
		butterfly.translate(xy._1, xy._2)
	}

//mouse click x
	def fnMusic(x: Int): ScalesNote = {
		(Note(x) before Note(x + 50)) before Note(x + 100)
	}

//just images
    val tallPuff = Ellipse(50, 80).stroke(Color.Clear).fill(Color.OliveGreen)
    val longPuff = Ellipse(80, 50).stroke(Color.Clear).fill(Color.OliveGreen)
    val puff = Ellipse(70, 70).stroke(Color.Clear).fill(Color.OliveGreen)
    val trunk = Rectangle(40, 200).stroke(Color.Mauve)
    val tree = ((((longPuff.tr on tallPuff.center) on longPuff.tl).bottom on (puff.left on puff).center).bottom on 
      trunk.center).translate(100, 200)
    val trees = (tree beside tree) beside (tree beside tree)

//just sounds
	val note = Note(200) before Note(300)
	val rep = note before note before note

//acts
    val background = Act(Anim(w, h)(MouseClickChanges, fnBackground))
    val car = Act(Anim(w, h)(ClockTickChanges(10, 10), fnCar))
    val butterfly = Act(Anim(w, h)(MousePosition, fnButterfly))
    val sounds = Act(Anim(w, h)(MouseClickX, fnMusic))
    val forest = Act(trees)
    val tune = Act(rep)

//main actor
    val actor = (background on (forest on (car.transform(2) on butterfly) on sounds)) seq tune

//running
actor.displayOn()