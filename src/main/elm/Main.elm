module Main exposing (Msg(..), main, update, view)

import Browser
import Html exposing (Html, button, div, text)
import Html.Events exposing (onClick)
import Http
import Protobuf.Decode as Decode
import Protobuf.Encode as Encode
import Snoble.ScalElambda exposing (..)


main =
    Browser.document { init = init, update = update, view = view, subscriptions = subscriptions }


apiRequest : RequestType -> (Response -> Msg) -> (Http.Error -> Msg) -> Cmd Msg
apiRequest awsRequest responseHandler httpErrorHandler =
    Http.request
        { method = "PUT"
        , url = "http://localhost:3000/"
        , headers = []
        , body = Http.bytesBody "application/x-protobuf" (Request (Just awsRequest) |> toRequestEncoder |> Encode.encode)
        , expect =
            Decode.expectBytes
                (\result ->
                    case result of
                        Ok response ->
                            responseHandler response

                        Err error ->
                            httpErrorHandler error
                )
                responseDecoder
        , timeout = Nothing
        , tracker = Nothing
        }


type Msg
    = GotHL HighLowResponse
    | WrongResponse
    | HttpError Http.Error


init : () -> ( String, Cmd Msg )
init flags =
    ( "Loading"
    , apiRequest (RequestTypeHl (HighLowRequest "up" 23))
        (\response ->
            case response.responseType of
                Just (ResponseTypeHl responseHl) ->
                    GotHL responseHl

                _ ->
                    WrongResponse
        )
        HttpError
    )


update msg model =
    case msg of
        GotHL result ->
            ( String.join " " [ result.first |> String.fromInt, result.second ], Cmd.none )

        HttpError error ->
            ( error |> Debug.toString, Cmd.none )

        WrongResponse ->
            ( "Wrong response", Cmd.none )


view model =
    { title = "ohhhh yeah!"
    , body =
        [ div []
            [ text model ]
        ]
    }


subscriptions model =
    Sub.none
