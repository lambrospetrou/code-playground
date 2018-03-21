defmodule FilterlsRunner do

  def run() do
    [{"filter-go", "filter-go"},
     {"filter.go", "filter.go"},
     {"filter-ex 0", "filter-ex-pat"},
     {"filter-ex 1", "filter-ex-split"},
     {"filter.rkt", "filter.rkt"},
     {"filter.py", "filter.py"},
     {"filter.rb", "filter.rb"}]
    |> Enum.each(&runBinary/1)
  end

  defp runBinary({bin, bin_output}) do
    #files = ["data.txt", "dataMM.txt"]
    files = String.Chars.to_string(:os.cmd('ls ./test-files')) |> String.split()
    files
    |> Enum.each(fn datafile ->
      IO.puts("Processing #{bin_output} - #{datafile}")
      :timer.tc(fn -> :os.cmd(String.to_charlist("./build/#{bin} < ./test-files/#{datafile} > ./output/#{datafile}.#{bin_output}.txt")) end)
      |> (fn ({micros, _output}) -> IO.puts("#{bin_output}-#{datafile} ::\t#{micros/1_000}ms\t#{micros}ns") end).()
    end)
  end

end

FilterlsRunner.run()
