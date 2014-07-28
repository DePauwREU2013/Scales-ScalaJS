package edu.depauw.scales.music

import scala.scalajs.js
import js.annotation.JSExport
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import edu.depauw.scales.reactive._

import Reactive._

// Graphic, Note, and Reactor all extends Scales
trait Scales {
	def act(time: Double = 0): Unit
	def duration: Double	
}

trait Performance {
	def act(t: Double = 0): Unit

	def on(that: Performance): ParallelAct = ParallelAct(this, that)

	def seq(that: Performance): SequentialAct = SequentialAct(this, that)

	def length: Double
}

/*
** Note, Graphic, and Reactor extend Scales
** Act extends Performance
** Performance handles Scales
*/
//note: took out duration because graphics, notes, and reactors should know their duration
//todo: get actual width and height
case class Act(scales: Scales, width: Double = Canvas.canvas.width, height: Double = Canvas.canvas.height) extends Performance {
	def length = scales.duration

	def act(t: Double = 0): Unit = {
		dom.setTimeout(() => scales.act(0), t * 1000)
	}

}

case class ParallelAct(one: Performance, two: Performance) extends Performance {
	def length = math.max(one.length, two.length)

	def act(t: Double = 0): Unit = {
		dom.setTimeout(() => {
			one.act(0)
			two.act(0)
		}, t * 1000)
	}

	
}

case class SequentialAct(first: Performance, second: Performance) extends Performance {
	def length = first.length + second.length

	def act(t: Double = 0): Unit = {
		dom.setTimeout(() => {
			first.act(0)
		}, t * 1000)

		val waitDuration = t + first.length
		dom.setTimeout(() => {
			second.act(0)
		}, waitDuration * 1000)

	}

}




// case class SeqActComposite(first: Scales, second: Scales) extends Performance {
// 	def act(): Unit = {
// 		first match {
// 			case x: Note => 
// 				val noteDuration = x.duration
// 				x.play(0)
// 				second match {
// 					case x: Note =>
// 						Reactor()
// 						x.play(noteDuration)
// 					case x: Graphic =>
// 						dom.setInterval(() => , noteDuration * 1000)
// 				}
// 			case x: Graphic => first.render(Canvas.ctx)
// 		}
// 	}
// }

// case class ParActComposite(first: Scales, second: Scales) extends Performance {

// }

// case class Act(scale: Scales) {
// 	def before(Scales)
// 	def par(that: Scales)
// }
