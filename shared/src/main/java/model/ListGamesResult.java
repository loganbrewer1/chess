package model;

import chess.ChessGame;

public record ListGamesResult(double gameID, String whiteUsername, String blackUsername, String gameName) {}
