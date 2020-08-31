import { HttpResponse, HttpRequest } from "./http";

export interface Controller {
  getAll(): Promise<HttpResponse>
  getById(httpRequest: HttpRequest): Promise<HttpResponse>
}
