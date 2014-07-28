package edu.depauw.scales.demo

import scala.scalajs.js
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import edu.depauw.scales.reactive._
import edu.depauw.scales.music._

object PrettyPicture {
	
	import Base._
	import Reactive._

	val w = Canvas.canvas.width
	val h = Canvas.canvas.height
	val radius = math.min(w, h) / 4
	val rSquared = radius * radius

	val wHalf = w / 2
	val hHalf = h / 2

	val incr = (radius) / 3

	def fnCircle(x: Double): Graphic = {
		val g = Ellipse(50, 50).fill(Color.HotPink)
		
		val quadOneX = x + wHalf
		if(quadOneX <= radius + wHalf) {
			val y = math.sqrt(rSquared - (x * x))
			return g.translate(quadOneX, hHalf - y)
		}

		val xPrime = x - radius
		val newXCoord = (radius + wHalf) - xPrime
		if(newXCoord >= wHalf) {
			val y = math.sqrt(rSquared - (xPrime * xPrime))

			dom.alert("radius - y is: " + (radius - y))
			return g.translate(newXCoord, hHalf + (radius - y))
		}

		//dom.alert("xprime: " + xPrime)
		//val quadTwoX = wHalf + x
		//dom.alert("quadTwoX: " + quadTwoX)
		// if(quadTwoX >= radius) {
		// 	val y = math.sqrt(rSquared - (x * x))
		// 	return g.translate(quadTwoX, hHalf - y)
		// }
		Text("")
	}

	def fnBackground(x: Double): Graphic = {
		val one = Rectangle(wHalf, hHalf).stroke(Color.Clear).strokeWidth(0).fill(Color.SkyBlue).tl
		val two = Rectangle(wHalf, hHalf).stroke(Color.Clear).strokeWidth(0).fill(Color.LightBlue).tl
		val three = Rectangle(wHalf, hHalf).stroke(Color.Clear).strokeWidth(0).fill(Color.Green).tl
		val four = Rectangle(wHalf, hHalf).stroke(Color.Clear).strokeWidth(0).fill(Color.DarkGreen).tl
		(one beside two) above (three beside four)
	}

	def fnCar(x: Double): Graphic = {
		val wheel1 = Ellipse(50, 50).fill(Color.Grey)
		val carBody = Polygon((0, 100), (75, 0), (150, 0), (200, 50), (250, 50), (250, 100)).strokeWidth(4).fill(Color.HotPink)
		(wheel1.bottom on (wheel1.center on carBody.bl).br).translate(400 + (x * 10), 300)
	}

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
}