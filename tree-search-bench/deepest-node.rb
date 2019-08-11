#!/usr/bin/env ruby

require 'json'

def deepestNode(root)
  def do_deep_rec(n, depth)
    if !n
      return [nil, 0]
    end
    if !n["left"] and !n["right"]
      return [n, depth]
    end

    left = do_deep_rec(n["left"], depth + 1)
    right = do_deep_rec(n["right"], depth + 1)

    if (left[1] > right[1])
      return left
    end
    return right
  end
  do_deep_rec(root, 1)[0]
end

def time_func()
  starting = Process.clock_gettime(Process::CLOCK_MONOTONIC)
  yield
  ending = Process.clock_gettime(Process::CLOCK_MONOTONIC)
  ending - starting
end

tree = JSON.parse(File.read(ARGV[0]))

result = nil
elapsed = time_func {
  result = deepestNode(tree)
}
puts "deepestNode: #{elapsed*1000}ms"

$stderr.puts(result.to_json)
