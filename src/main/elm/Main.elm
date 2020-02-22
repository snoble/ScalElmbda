module Main exposing (Msg(..), main, update, view)

import Browser
import Html exposing (Html, button, div, text)
import Html.Events exposing (onClick)
import Http
import Protobuf.Decode as Decode
import Protobuf.Encode as Encode
import Snoble.Scalambda exposing (..)


main =
    Browser.document { init = init, update = update, view = view, subscriptions = subscriptions }


type Msg
    = GotText (Result Http.Error Response)


init : () -> ( String, Cmd Msg )
init flags =
    ( ""
    , Http.request
        { method = "PUT"
        , url = "http://localhost:3000/"
        , headers = []
        , body = Http.bytesBody "application/x-protobuf" (Request (Just (RequestTypeHlRequest (HighLowRequest "up" 23))) |> toRequestEncoder |> Encode.encode)
        , expect = Decode.expectBytes GotText responseDecoder
        , timeout = Nothing
        , tracker = Nothing
        }
    )


update msg model =
    case msg of
        GotText (Ok result) ->
            ( String.join " " [ result.first |> String.fromInt, result.second ], Cmd.none )

        GotText (Err error) ->
            ( error |> Debug.toString, Cmd.none )


view model =
    { title = "ohhhh yeah!"
    , body =
        [ div []
            [ text model ]
        ]
    }


subscriptions model =
    Sub.none
