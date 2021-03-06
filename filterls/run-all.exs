defmodule FilterlsRunner do

  def run() do
    [
     {"filter-go", "filter-go"},
     #{"filter.go", "filter.go"},
     {"filter-rs", "filter-rs"},
     {"filter-cr", "filter-cr"},
     {"filter-kotlin-jar", "filter-kotlin-jar"},
     #{"filter-fsharp", "filter-fsharp"},
     #{"filter.rb", "filter.rb"},
     #{"filter.py", "filter.py"},
     #{"filter.js", "filter.js"},
     {"filter.rkt", "filter.rkt"},
     #{"filter.cljs", "filter.cljs"},
     #{"filter-lumo.cljs", "filter-lumo.cljs"},
     #{"filter.clj", "filter.clj"},
     #{"filter-clj-uberjar", "filter-clj-uberjar"},
     #{"filter-clj-uberjar-native", "filter-clj-uberjar-native"},
     #{"filter-ex 0", "elixir-pat"},
     #{"filter-ex 1", "elixir-split"},
    ]
    |> Enum.each(&runBinary/1)
  end

  defp runBinary({bin, bin_output}) do
    #files = ["data.txt", "dataMM.txt", "data.txt100000.txt", "data.txt1000000.txt"]
    #files = ["data.txt100000.txt", "data.txt1000000.txt"]
    files = ["data.txt", "dataMM.txt", "data.txt100000.txt"]
    #files = ["data.txt1000000.txt"]
    # files = String.Chars.to_string(:os.cmd('ls ./test-files')) |> String.split()
    files
    |> Enum.each(fn datafile ->
      :timer.tc(fn -> :os.cmd(String.to_charlist(getCmd(bin, bin_output, datafile))) end)
      |> (fn ({micros, _output}) -> IO.puts("#{bin_output}-#{datafile}\t[#{micros/1_000}ms\t#{micros}ns]") end).()
    end)
  end

  defp getCmd("filter-ex 0" = bin, bin_output, datafile), do:
    "./build/#{bin} ./test-files/#{datafile} ./output/#{datafile}.#{bin_output}.txt"
  defp getCmd("filter-ex 1" = bin, bin_output, datafile), do:
    "./build/#{bin} ./test-files/#{datafile} ./output/#{datafile}.#{bin_output}.txt"
  defp getCmd("filter-clj-uberjar", bin_output, datafile), do:
    "java -jar ./build/filter-clj-uberjar.jar < ./test-files/#{datafile} > ./output/#{datafile}.#{bin_output}.txt"
  defp getCmd("filter-kotlin-jar", bin_output, datafile), do:
    "java -jar ./build/filter-kotlin.jar < ./test-files/#{datafile} > ./output/#{datafile}.#{bin_output}.txt"
  defp getCmd(bin, bin_output, datafile), do:
    "./build/#{bin} < ./test-files/#{datafile} > ./output/#{datafile}.#{bin_output}.txt"

end

FilterlsRunner.run()
