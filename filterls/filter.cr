def is_valid(line)
  segs = line.split(' ', limit: 3, remove_empty: true)
  segs.size > 1 ? segs[1].to_i64 { -1 } > 10 : false
end

ARGF.each_line do |line|
  if is_valid(line)
    puts line
  end
end
