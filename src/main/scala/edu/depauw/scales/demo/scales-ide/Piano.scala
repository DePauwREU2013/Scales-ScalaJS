val canvas = Canvas.canvas
val width = canvas.width
val height = canvas.height

val numKeys = 16
val kh = height / 2
val kw = canvas.width / numKeys

val bkh = kh / 2
val bkw = kw / 2

val whiteKey = Rectangle(kw, kh).strokeWidth(5).stroke(Color.Black).fill(Color.White).tl
val blackKey = Rectangle(bkw, bkh).strokeWidth(5).stroke(Color.Black).fill(Color.Black).tl

val twoBlackKeys = blackKey.translate(3 * kw / 4, 0) beside blackKey.tl.pad(2)
val threeBlackKeys =  blackKey beside blackKey.tl.pad(2) beside blackKey
val fiveBlackKeys = twoBlackKeys.pad(1) beside threeBlackKeys




val clearKey = Rectangle(2 * kw - (kw / 2), kh / 2).stroke(Color.Clear).fill(Color.Clear).tl
val clearKey2 = Rectangle(kw, kh / 2).stroke(Color.Clear).fill(Color.Clear).tl
val stwobk = clearKey beside twoBlackKeys
val sthreebk = clearKey2 beside threeBlackKeys
val singleBlackKey = clearKey beside blackKey




def generateWhiteKeys(i: Int, num: Int): Graphic = ((i+1) == num) match {
    case true => whiteKey
    case false => whiteKey beside generateWhiteKeys(i + 1, num)
}

def fnGetNote(xy: (Int, Int)): ScalesNote = {
    val x = xy._1
    val y = xy._2
    
    if(y < kh) {
        lazy val bk1 = bkw + bkw / 2
        lazy val bk2 = bk1 + kw
        lazy val bk3 = bk2 + kw + kw
        lazy val bk4 = bk3 + kw
        lazy val bk5 = bk4 + kw
        lazy val bk6 = bk5 + kw + kw
        lazy val bk7 = bk6 + kw
        lazy val bk8 = bk7 + kw + kw
        lazy val bk9 = bk8 + kw
        lazy val bk10 = bk9 + kw
        lazy val bk11 = bk10 + kw + kw
        
        //first white key
        if(x < bk1 || (x < kw && y > bkh)) 
            C.>(2).setDuration(.2)
        
        //first black key
        else if(y < bkh && (x > bk1 && x < bk1 + bkw))
            Cs.>(2).setDuration(.2)
        
        //second white key
        else if(x < bk2 || (x < 2 * kw && y > bkh)) 
            D.>(2).setDuration(.2)
        
        //second black key
        else if(y < bkh && (x > bk2 && x < bk2 + bkw))
            Ds.>(2).setDuration(.2)
        
        //third white key
        else if(x < kw * 3)
            E.>(2).setDuration(.2)
        
        //fourth white key
        else if(x < bk3 || (x < kw * 4 && y > bkh))
            F.>(2).setDuration(.2)
        
        //third black key
        else if(y < bkh && (x > bk3 && x < bk3 + bkw))
            Fs.>(2).setDuration(.2)
            
        //fifth white key
        else if(x < bk4 || (x < kw * 5 && y > bkh))
            G.>(2).setDuration(.2)
        
        //fourth black key
        else if(y < bkh && (x > bk4 && x < bk4 + bkw))
            Gs.>(2).setDuration(.2)
        
        //sixth white key
        else if(x < bk5 || (x < kw * 6 && y > bkh))
            A.>(2).setDuration(.2)
        
        //fifth black key
        else if(y < bkh && (x > bk5 && x < bk5 + bkw))
            As.>(2).setDuration(.2)
        
        //seventh white key
        else if(x < kw * 7)
            B.>(2).setDuration(.2)
        
        //eighth white key
        else if(x < bk6 || (x < kw * 8 && y > bkh))
            C.>(3).setDuration(.2)
        
        //sixth black key
        else if(y < bkh && (x > bk6 && x < bk6 + bkw))
            Cs.>(3).setDuration(.2)
        
        //ninth white key
        else if(x < bk7 || (x < kw * 9 && y > bkh))
            D.>(3).setDuration(.2)
        
        //seventh black key
        else if(y < bkh && (x > bk7 && x < bk7 + bkw))
            Ds.>(3).setDuration(.2)
            
        //tenth white key
        else if(x < kw * 10)
            E.>(3).setDuration(.2)
            
        //eleventh white key
        else if(x < bk8 || (x < kw * 11 && x > bkh))
            F.>(3).setDuration(.2)
            
        //eighth black key
        else if(y < bkh && (x > bk8 && x < bk8 + bkw))
            Fs.>(3).setDuration(.2)
        
        //twelfth white key
        else if(x < bk9 || (x < kw * 12 && y > bkh))
            G.>(3).setDuration(.2)
        
        //ninth black key
        else if(y < bkh && (x > bk9 && x < bk9 + bkw))
            Gs.>(3).setDuration(.2)
        
        //thirteenth white key
        else if(x < bk10 || (x < kw * 13 && y > bkh))
            A.>(3).setDuration(.2)
        
        //tenth black key
        else if(y < bkh && (x > bk10 && x < bk10 + bkw))
            As.>(3).setDuration(.2)
            
        //fourteenth white key
        else if(x < kw * 14)
            B.>(3).setDuration(.2)
        
        //fifteenth white key
        else if(x < bk11 || (x < kw * 15 && y > bkh)) 
            C.>>>>.setDuration(.2)
        
        //eleventh black key
        else if(y < bkh && (x > bk11 && x < bk11 + bkw)) 
            Cs.>>>>.setDuration(.2)
        
        //sixteenth white key
        else D.>>>>.setDuration(.2)
        
    }
    else Silent
}

//rendering
lazy val ks = (fiveBlackKeys beside stwobk beside sthreebk beside singleBlackKey) on generateWhiteKeys(0, numKeys)

//ks.render(Canvas.ctx)

val playing = Anim(width, height)(Reactive.MouseClick, fnGetNote)//.act()

(Act(ks) on Act(playing)).act()