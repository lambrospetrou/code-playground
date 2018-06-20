#!/usr/bin/env julia

function filterLine(s)
	segs = split(s, " ", limit=3)
	if length(segs) > 1
		num = tryparse(Int, segs[2])
		num.hasvalue ? num.value > 10 : false
	else
		false
	end
end

for l in eachline()
	if filterLine(l)
		print("$l\n")
	end
end
