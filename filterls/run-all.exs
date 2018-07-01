defmodule FilterlsRunner do

  def run() do
    [{"filter-go", "filter-go"},
    #  {"filter.go", "filter.go"},
     {"filter-rs", "filter-rs"},
     {"filter-cr", "filter-cr"},
     {"filter.rb", "filter.rb"},
     {"filter.py", "filter.py"},
     {"filter-ex 0", "elixir-pat"},
     {"filter-ex 1", "elixir-split"},
     {"filter.rkt", "filter.rkt"},
    ]
    |> Enum.each(&runBinary/1)
  end

  defp runBinary({bin, bin_output}) do
    # files = ["data.txt", "dataMM.txt", "data.txt100000.txt"]
    files = ["data.txt"]
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
  defp getCmd(bin, bin_output, datafile), do:
    "./build/#{bin} < ./test-files/#{datafile} > ./output/#{datafile}.#{bin_output}.txt"

end

FilterlsRunner.run()
