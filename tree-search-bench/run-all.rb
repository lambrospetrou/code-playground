#!/usr/bin/env ruby

class TreeSearchRunner

  def run()
    dataset = [
      "xsmall.json",
      "small.json",
      "medium.json",
      "large.json",
      "xlarge.json",
    ].map {|input| "test-files/#{input}" }

    runners = [
      {cmd: "build/deepest-node.js", out: "deepest-node.js"},
      {cmd: "build/deepest-node.rb", out: "deepest-node.rb"},
    ]

    runners.each {|r|
      dataset.each {|dataset|
        datasetName = dataset["test-files/".length..(-1)]
        runnerStdout = ""
        elapsed = time_func {
          runnerStdout = `#{r[:cmd]} #{dataset} 2> output/#{datasetName}.#{r[:out]}.txt`
        }
        puts "#{r[:out]} \t#{datasetName} \t#{elapsed}s \t#{runnerStdout.chomp()}\n"
      }
    }
  end

  def time_func()
    starting = Process.clock_gettime(Process::CLOCK_MONOTONIC)
    yield
    ending = Process.clock_gettime(Process::CLOCK_MONOTONIC)
    ending - starting
  end

end

TreeSearchRunner.new.run()
