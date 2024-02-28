package model;

import chess.ChessGame;

public record ListGamesResult(int gameID, String whiteUsername, String blackUsername, String gameName) {}
