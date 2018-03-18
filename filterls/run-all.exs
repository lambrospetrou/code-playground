defmodule FilterlsRunner do

  def copyFile([filename]), do: copyFile([filename, 10])
  def copyFile([filename, times]) do
    b = File.read!(filename)

    File.open("#{filename}#{times}.txt", [:write], fn file ->
      Enum.each(1..times, fn _ ->
        IO.binwrite(file, b)
      end)
    end)
  end

  def run() do
    [{"filter-go", "filter-go"},
     {"filter-ex 0", "filter-ex-pat"},
     {"filter-ex 1", "filter-ex-split"},
     {"filter-racket", "filter-racket"},
     {"filter-racket.rkt", "filter-racket.rkt"}]
    |> Enum.each(&runBinary/1)
  end

  defp runBinary({bin, bin_output}) do
    #files = ["data.txt", "dataMM.txt"]
    files = String.Chars.to_string(:os.cmd('ls ./test-files')) |> String.split()
    files
    |> Enum.each(fn datafile ->
      IO.puts("Processing #{bin_output} - #{datafile}")
      :os.cmd(String.to_charlist("time ./build/#{bin} < ./test-files/#{datafile} > ./output/#{datafile}.#{bin_output}.txt"))
      |> IO.puts
    end)
  end

end

#FilterlsRunner.copyFile(System.argv)
FilterlsRunner.run()
