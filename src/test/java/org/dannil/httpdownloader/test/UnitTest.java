package org.dannil.httpdownloader.test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedList;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.repository.UserRepository;
import org.dannil.httpdownloader.service.IDownloadService;
import org.dannil.httpdownloader.service.IRegisterService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conf/xml/spring-context.xml")
public final class UnitTest {

	private final String DOWNLOAD_URL = "http://dannils.se/pi.zip";

	@Autowired
	private IDownloadService downloadService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IRegisterService registerService;

	@Test
	public final void findDownloadsByUser() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		final LinkedList<Download> downloads = new LinkedList<Download>();
		downloads.add(new Download("pi", this.DOWNLOAD_URL));

		final User user = new User("example@example.com", "test", "exampleFirst", "exampleLast", downloads);
		final User registered = this.registerService.save(user);

		final LinkedList<Download> result = this.downloadService.findByUser(registered);

		// cleanup
		this.userRepository.delete(registered);

		Assert.assertNotNull(result);
	}
}
