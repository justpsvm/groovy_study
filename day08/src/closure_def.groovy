def isHandle = {it % 2 == 0}

def total(event){
	for(i in 1..10){
		if(event(i)){
			println i
		}
	}
}

// total(isHandle)

total(){it % 2 == 0}




