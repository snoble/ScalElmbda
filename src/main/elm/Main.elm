module Main exposing (Msg(..), main, update, view)

import Browser
import Html exposing (Html, button, div, text)
import Html.Events exposing (onClick)
import Http
import Protobuf.Decode as Decode
import Snoble.Scalambda exposing (..)


main =
    Browser.document { init = init, update = update, view = view, subscriptions = subscriptions }


type Msg
    = GotText (Result Http.Error Simple)


init : () -> ( String, Cmd Msg )
init flags =
    ( "", Http.get { url = "http://localhost:3000/hello", expect = Decode.expectBytes GotText simpleDecoder } )


update msg model =
    case msg of
        GotText (Ok result) ->
            ( String.join " " [ result.first, result.second ], Cmd.none )

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
