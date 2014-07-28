package edu.depauw.scales.demo

import scala.scalajs.js
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import edu.depauw.scales.reactive._
import edu.depauw.scales.music._

object ActDemo {
	import Base._
	import Reactive._

	val g = (Ellipse(100, 100).fill(Color.Salmon)).tl
	val note = C.>>>>
	val lessObnoxiousNote = Note(175, 4)

	val actor = (Act(g) on Act(note))
	val actor2 = (Act(lessObnoxiousNote) seq Act(g))
}
