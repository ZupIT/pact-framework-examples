import { HttpResponse } from "../protocols/http";

export const badRequest = (error: Error): HttpResponse => ({
  statusCode: 400,
  body: error
})

export const success = (data: any): HttpResponse => ({
  statusCode: 200,
  body: data
})

export const notFound = (message: any): HttpResponse => ({
  statusCode: 404,
  body: message
})