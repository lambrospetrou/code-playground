defmodule FilterlsIex do
  @moduledoc """
  Documentation for FilterlsIex.
  """

  @doc """
  If argv[0] == 1 use &splitter/1, otherwise use &patterner/1
  """
  def main([]), do: main(["0"])
  def main([what]) do
    if what == "1" do
      process(&splitter/1)
    else
      process(&patterner/1)
    end
  end
  def main([what, datafile, outputfile]) do
    if what == "1" do
      process(&splitter/1, datafile, outputfile)
    else
      process(&patterner/1, datafile, outputfile)
    end
  end

  defp process(filterer) do
    IO.binstream(:stdio, :line)
    |> Stream.filter(filterer)
    |> Stream.into(IO.binstream(:stdio, :line))
    |> Stream.run()
  end

  defp process(filterer, inputfile, outputfile) do
    File.stream!(inputfile, read_ahead: 10_000_000)
    |> Stream.filter(filterer)
    |> Stream.into(File.stream!(outputfile, [:delayed_write]))
    |> Stream.run()
  end

  #############################################
  # Use pattern matching to match each line
  defp patterner(<<>>), do: false
  defp patterner(line), do: do_patterner(line, <<>>, 0)

  defp do_patterner(<<>>, <<>>, 0), do: false

  defp do_patterner(<<>>, n, 1), do: is_valid(n)

  defp do_patterner(<<?\s::utf8, ?\s::utf8, rest::binary>>, num, spaces),
    do: do_patterner(<<?\s, rest::binary>>, num, spaces)

  defp do_patterner(<<?\s::utf8, rest::binary>>, num, spaces),
    do: do_patterner(rest, num, spaces + 1)

  defp do_patterner(<<n::utf8, rest::binary>>, num, 1),
    do: do_patterner(rest, <<num::binary, n>>, 1)

  # we reached a second space so go to base case in order to return
  defp do_patterner(<<_rest::binary>>, num, 2), do: do_patterner(<<>>, num, 1)
  defp do_patterner(<<_n::utf8, rest::binary>>, num, spaces), do: do_patterner(rest, num, spaces)

  #################################################
  # Use String.split()

  defp splitter(line) do
    parts = String.split(line, " ", parts: 3, trim: true)
    case parts do
      [_ | [c2 | _rest]] -> is_valid(c2)
      _ -> false
    end
  end

  defp is_valid(num_str) do
    case Integer.parse(num_str) do
      {num, _} when num > 10 -> true
      _ -> false
    end
  end

end
