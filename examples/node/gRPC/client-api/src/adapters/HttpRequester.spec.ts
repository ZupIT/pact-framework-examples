import axios from 'axios';
import { HtttpRequester } from './HttpRequester';

jest.mock('axios');

const ACTION_PATH = '/package.service/action';
const MOCK_SERVER_BASE_URL = 'http://localhost:8888';
const GRPC_REST_PREFIX_CONVENTION = '/grpc';

describe('HttpRequester', () => {
  let httpRequester: HtttpRequester;

  beforeAll(() => {
    httpRequester = new HtttpRequester(MOCK_SERVER_BASE_URL, {
      method_definition: {
        path: ACTION_PATH,
        requestStream: false,
        responseStream: false,
        requestSerialize: arg => Buffer.of(arg),
        responseDeserialize: buffer => Buffer.from(buffer),
      },
    });
  });

  it('should call http request based on remote procedure call', () => {
    const MESSAGE_DATA = 'ok';
    const mockedAxios = axios as jest.Mocked<typeof axios>;
    const mockValue = { data: MESSAGE_DATA };
    mockedAxios.post.mockImplementationOnce(() => Promise.resolve(mockValue));

    httpRequester.sendMessage(MESSAGE_DATA, () => {
      // empty
    });

    expect(axios.post).toHaveBeenCalledWith(
      `${MOCK_SERVER_BASE_URL}${GRPC_REST_PREFIX_CONVENTION}${ACTION_PATH}`,
      MESSAGE_DATA,
      {
        headers: { 'Content-Type': 'application/json; charset=utf-8' },
      },
    );
  });
});
