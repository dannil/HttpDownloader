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
import org.dannil.httpdownloader.test.utility.TestUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conf/xml/spring-context.xml")
public final class DatabaseTest {

	@Autowired
	private IDownloadService downloadService;

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private UserRepository userRepository;

	@Test
	public final void findDownloadsByUser() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		final Download download = new Download(TestUtility.getDownload());

		final User user = new User(TestUtility.getUser());
		final User registered = this.registerService.save(user);

		download.setUser(registered);

		this.downloadService.save(download);

		final LinkedList<Download> result = this.downloadService.findByUser(registered);

		Assert.assertEquals(1, result.size());
	}

}
