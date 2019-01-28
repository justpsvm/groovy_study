
/*
def sum(a,int ...b){
	for(v in b){
		a += v
	}
	a
}*/


def sum(a,int ...b){
	for(v in b){
		a += v
	}
	a
}

//println sum(1,2,3)

println sum(1,[2,3] as int[])

