

/*
def sum = 0
for(i in 1..10){
	if(i % 2 == 0){
		sum += i
	}
}

def sum = 0
for(i in 1..10){
	if(i % 2 != 0){
		sum *= i
	}
}*/


def total(i,event){
	for(def j = 1; j <= i; j++){
		if(j % 2 == 0) event(j)
	}
}

def sum = 0

// total(10){sum += it}

total(10){number -> sum += number}

println "sum: $sum"

