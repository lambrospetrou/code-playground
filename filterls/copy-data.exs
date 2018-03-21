defmodule CopyFile do

  def copyFile([filename]), do: copyFile([filename, 10])
  def copyFile([filename, times]) do
    b = File.read!(filename)
    times = String.to_integer(times)
    File.open("#{filename}#{times}.txt", [:write], fn file ->
      Enum.each(1..times, fn _ ->
        IO.binwrite(file, b)
      end)
    end)
  end

end

CopyFile.copyFile(System.argv)
