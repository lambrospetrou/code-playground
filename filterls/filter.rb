#!/usr/bin/env ruby

def str_to_i(str)
	num = str.to_i
  	num.to_s == str ? num : nil
end

def is_valid(line)
	segs = line.split()
	if segs.length > 1
		num = str_to_i(segs[1])
		return true if num != nil and num > 10
	end
	return false
end

ARGF.each do |line|
	puts line if is_valid(line)
end
