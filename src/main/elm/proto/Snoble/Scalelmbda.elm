{- !!! DO NOT EDIT THIS FILE MANUALLY !!! -}


module Snoble.Scalelmbda exposing
    ( HighLowRequest, HighLowResponse, ListOfStringsRequest, ListOfStringsResponse, RequestType(..), Request, ResponseType(..), Response
    , highLowRequestDecoder, highLowResponseDecoder, listOfStringsRequestDecoder, listOfStringsResponseDecoder, requestDecoder, responseDecoder
    , toHighLowRequestEncoder, toHighLowResponseEncoder, toListOfStringsRequestEncoder, toListOfStringsResponseEncoder, toRequestEncoder, toResponseEncoder
    )

{-| ProtoBuf module: `Snoble.Scalelmbda`

This module was generated automatically using

  - [`protoc-gen-elm`](https://www.npmjs.com/package/protoc-gen-elm) 1.0.0-beta-2
  - `protoc` 3.9.0
  - the following specification file: `src/main/proto/simple.proto`

To run it use [`elm-protocol-buffers`](https://package.elm-lang.org/packages/eriktim/elm-protocol-buffers/1.1.0) version 1.1.0 or higher.


# Model

@docs HighLowRequest, HighLowResponse, ListOfStringsRequest, ListOfStringsResponse, RequestType, Request, ResponseType, Response


# Decoder

@docs highLowRequestDecoder, highLowResponseDecoder, listOfStringsRequestDecoder, listOfStringsResponseDecoder, requestDecoder, responseDecoder


# Encoder

@docs toHighLowRequestEncoder, toHighLowResponseEncoder, toListOfStringsRequestEncoder, toListOfStringsResponseEncoder, toRequestEncoder, toResponseEncoder

-}

import Protobuf.Decode as Decode
import Protobuf.Encode as Encode



-- MODEL


{-| `HighLowRequest` message
-}
type alias HighLowRequest =
    { high : String
    , low : Int
    }


{-| `HighLowResponse` message
-}
type alias HighLowResponse =
    { first : Int
    , second : String
    }


{-| `ListOfStringsRequest` message
-}
type alias ListOfStringsRequest =
    { parts : List String
    }


{-| `ListOfStringsResponse` message
-}
type alias ListOfStringsResponse =
    { concatenated : String
    , length : Int
    }


{-| RequestType
-}
type RequestType
    = RequestTypeHl HighLowRequest
    | RequestTypeStrings ListOfStringsRequest


{-| `Request` message
-}
type alias Request =
    { requestType : Maybe RequestType
    }


{-| ResponseType
-}
type ResponseType
    = ResponseTypeHl HighLowResponse
    | ResponseTypeStrings ListOfStringsResponse


{-| `Response` message
-}
type alias Response =
    { responseType : Maybe ResponseType
    }



-- DECODER


{-| `HighLowRequest` decoder
-}
highLowRequestDecoder : Decode.Decoder HighLowRequest
highLowRequestDecoder =
    Decode.message (HighLowRequest "" 0)
        [ Decode.optional 1 Decode.string setHigh
        , Decode.optional 2 Decode.int32 setLow
        ]


{-| `HighLowResponse` decoder
-}
highLowResponseDecoder : Decode.Decoder HighLowResponse
highLowResponseDecoder =
    Decode.message (HighLowResponse 0 "")
        [ Decode.optional 1 Decode.int32 setFirst
        , Decode.optional 2 Decode.string setSecond
        ]


{-| `ListOfStringsRequest` decoder
-}
listOfStringsRequestDecoder : Decode.Decoder ListOfStringsRequest
listOfStringsRequestDecoder =
    Decode.message (ListOfStringsRequest [])
        [ Decode.repeated 1 Decode.string .parts setParts
        ]


{-| `ListOfStringsResponse` decoder
-}
listOfStringsResponseDecoder : Decode.Decoder ListOfStringsResponse
listOfStringsResponseDecoder =
    Decode.message (ListOfStringsResponse "" 0)
        [ Decode.optional 1 Decode.string setConcatenated
        , Decode.optional 2 Decode.int32 setLength
        ]


{-| `Request` decoder
-}
requestDecoder : Decode.Decoder Request
requestDecoder =
    Decode.message (Request Nothing)
        [ Decode.oneOf
            [ ( 1, Decode.map RequestTypeHl highLowRequestDecoder )
            , ( 2, Decode.map RequestTypeStrings listOfStringsRequestDecoder )
            ]
            setRequestType
        ]


{-| `Response` decoder
-}
responseDecoder : Decode.Decoder Response
responseDecoder =
    Decode.message (Response Nothing)
        [ Decode.oneOf
            [ ( 1, Decode.map ResponseTypeHl highLowResponseDecoder )
            , ( 2, Decode.map ResponseTypeStrings listOfStringsResponseDecoder )
            ]
            setResponseType
        ]



-- ENCODER


{-| `HighLowRequest` encoder
-}
toHighLowRequestEncoder : HighLowRequest -> Encode.Encoder
toHighLowRequestEncoder model =
    Encode.message
        [ ( 1, Encode.string model.high )
        , ( 2, Encode.int32 model.low )
        ]


{-| `HighLowResponse` encoder
-}
toHighLowResponseEncoder : HighLowResponse -> Encode.Encoder
toHighLowResponseEncoder model =
    Encode.message
        [ ( 1, Encode.int32 model.first )
        , ( 2, Encode.string model.second )
        ]


{-| `ListOfStringsRequest` encoder
-}
toListOfStringsRequestEncoder : ListOfStringsRequest -> Encode.Encoder
toListOfStringsRequestEncoder model =
    Encode.message
        [ ( 1, Encode.list Encode.string model.parts )
        ]


{-| `ListOfStringsResponse` encoder
-}
toListOfStringsResponseEncoder : ListOfStringsResponse -> Encode.Encoder
toListOfStringsResponseEncoder model =
    Encode.message
        [ ( 1, Encode.string model.concatenated )
        , ( 2, Encode.int32 model.length )
        ]


toRequestTypeEncoder : RequestType -> ( Int, Encode.Encoder )
toRequestTypeEncoder model =
    case model of
        RequestTypeHl value ->
            ( 1, toHighLowRequestEncoder value )

        RequestTypeStrings value ->
            ( 2, toListOfStringsRequestEncoder value )


{-| `Request` encoder
-}
toRequestEncoder : Request -> Encode.Encoder
toRequestEncoder model =
    Encode.message
        [ Maybe.withDefault ( 0, Encode.none ) <| Maybe.map toRequestTypeEncoder model.requestType
        ]


toResponseTypeEncoder : ResponseType -> ( Int, Encode.Encoder )
toResponseTypeEncoder model =
    case model of
        ResponseTypeHl value ->
            ( 1, toHighLowResponseEncoder value )

        ResponseTypeStrings value ->
            ( 2, toListOfStringsResponseEncoder value )


{-| `Response` encoder
-}
toResponseEncoder : Response -> Encode.Encoder
toResponseEncoder model =
    Encode.message
        [ Maybe.withDefault ( 0, Encode.none ) <| Maybe.map toResponseTypeEncoder model.responseType
        ]



-- SETTERS


setHigh : a -> { b | high : a } -> { b | high : a }
setHigh value model =
    { model | high = value }


setLow : a -> { b | low : a } -> { b | low : a }
setLow value model =
    { model | low = value }


setFirst : a -> { b | first : a } -> { b | first : a }
setFirst value model =
    { model | first = value }


setSecond : a -> { b | second : a } -> { b | second : a }
setSecond value model =
    { model | second = value }


setParts : a -> { b | parts : a } -> { b | parts : a }
setParts value model =
    { model | parts = value }


setConcatenated : a -> { b | concatenated : a } -> { b | concatenated : a }
setConcatenated value model =
    { model | concatenated = value }


setLength : a -> { b | length : a } -> { b | length : a }
setLength value model =
    { model | length = value }


setRequestType : a -> { b | requestType : a } -> { b | requestType : a }
setRequestType value model =
    { model | requestType = value }


setResponseType : a -> { b | responseType : a } -> { b | responseType : a }
setResponseType value model =
    { model | responseType = value }