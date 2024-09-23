package com.sb.maps.dtos;

public record SignUpDto (String firstName, String lastName, String login, char[] password, Location location) { }
