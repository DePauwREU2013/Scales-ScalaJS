package edu.depauw.scales.demo

import scala.scalajs.js
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import edu.depauw.scales.reactive._
import edu.depauw.scales.music._
import edu.depauw.scales.act._

object ActDemo {
	import Base._
	import Reactive._

	val g = Ellipse(100, 100).tl
	val poly = Polygon(
		(0, 0), (0, 20), (20, 40), (40, 60)
	).fill(Color.Violet)
	val reallyComplexGraphic = (g above g.fill(Color.Blue)) above (g.fill(Color.Red))
	val trulyComplexGraphic = reallyComplexGraphic beside reallyComplexGraphic

	val note = C.>(2).setVolume(.75)
	val lessObnoxiousNote = Note(175, 4)

	val timerEvent = Anim(200, 200)(ClockTick(1, 10), (t: Double) => { g.fill(Color.SeaGreen).translate(t * 20, 100) } )
	val clickEvent = Anim(0, 0)(MouseClick, (xy: (Int, Int)) => { g.fill(Color.Violet).center.translate(xy._1, xy._2) } )



	val actor = (Act(g) on Act(note))
	val actor2 = (Act(lessObnoxiousNote) seq Act(g))
	val actor3 = (actor2 on Act(timerEvent)) seq Act(note)
	
	val actor4Dash1 = Act(timerEvent) on Act(clickEvent)
	val actor4Dash2 = Act(lessObnoxiousNote) on Act(trulyComplexGraphic)
	val actor4Dash3 = Act(poly) on Act(note)
	val actor4 = (actor4Dash1 seq (actor4Dash2 on actor4Dash3))

	val actor5 = actor4.transform(2)

	val actor6 = Act(trulyComplexGraphic) seq Act(lessObnoxiousNote)

}
