open System
open System.IO

let processLine (line: string, output: StreamWriter) =
    let segs = line.Split([|" "|], 3, StringSplitOptions.RemoveEmptyEntries)
    if segs.Length >= 2 then
        let mutable value = 0L
        let isNum = Int64.TryParse(segs.[1], &value)
        if isNum && value > 10L then output.WriteLine(line)

let main() = 
    let BUFSIZE = 8192
    let output = new StreamWriter(Console.OpenStandardOutput(BUFSIZE))
    let input = new StreamReader(Console.OpenStandardInput(BUFSIZE))
    let mutable eof = false
    while not eof do
        let line = input.ReadLine()
        if not (isNull line) then processLine(line, output) else eof <- true
    output.Close()

main()
