namespace AwsDotnetFsharp
open Amazon.Lambda.Core

[<assembly:LambdaSerializer(typeof<Amazon.Lambda.Serialization.Json.JsonSerializer>)>]
do ()

type Request = { Key1 : string; Key2 : string; Key3 : string }
type Response = { Message : string; Request : Request }

module Handler =
    open System
    open System.IO
    open System.Text

    let add a b = a + b

    let hello(request:Request) =
        { Message="Go Serverless v1.0! Your function executed successfully!"
          Request=request }

    let helloAdd(req:Request) =
        {
          Message = sprintf "Hello handler working with result %d!" (add 10 20)
          Request = req
        }
